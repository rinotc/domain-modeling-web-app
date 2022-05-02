package dev.tchiba.sdmt.application.interactors.domainmodel.add

import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.core.domainmodel.{DomainModel, DomainModelRepository}
import dev.tchiba.sdmt.usecase.domainmodel.create.{
  CreateDomainModelInput,
  CreateDomainModelOutput,
  CreateDomainModelUseCase
}

import javax.inject.Inject

class CreateDomainModelInteractor @Inject() (
    boundedContextRepository: BoundedContextRepository,
    domainModelRepository: DomainModelRepository
) extends CreateDomainModelUseCase {

  override def handle(input: CreateDomainModelInput): CreateDomainModelOutput = {
    boundedContextRepository.findById(input.boundedContextId) match {
      case None => CreateDomainModelOutput.NoSuchBoundedContext(input.boundedContextId)
      case Some(context) =>
        val newDomainModel = DomainModel.create(
          boundedContextId = context.id,
          japaneseName = input.japaneseName,
          englishName = input.englishName,
          specification = input.specification
        )
        domainModelRepository.findByEnglishName(newDomainModel.englishName, newDomainModel.boundedContextId) match {
          case Some(_) => CreateDomainModelOutput.ConflictEnglishName(newDomainModel.englishName)
          case None =>
            domainModelRepository.insert(newDomainModel)
            CreateDomainModelOutput.Success(newDomainModel)
        }
    }
  }
}
