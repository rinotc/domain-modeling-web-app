package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.domainmodel.create.{CreateDomainModelOutput, CreateDomainModelUseCase}
import interfaces.api.QueryValidator
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateDomainModelApiController @Inject() (
    cc: ControllerComponents,
    createDomainModelUseCase: CreateDomainModelUseCase
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers

  def action(id: String): Action[CreateDomainModelRequest] = Action(CreateDomainModelRequest.validateJson) {
    implicit request =>
      QueryValidator.sync {
        BoundedContextId.validate(id)
      } { boundedContextId =>
        val input = request.body.input(boundedContextId)
        createDomainModelUseCase.handle(input) match {
          case CreateDomainModelOutput.NoSuchBoundedContext(id) =>
            NotFound(ErrorResponse(s"no such bounded context id: $id").json.play)
          case CreateDomainModelOutput.ConflictEnglishName(englishName) =>
            Conflict(ErrorResponse(s"english name $englishName is conflicted in bounded context.").json.play)
          case CreateDomainModelOutput.Success(newDomainModel) =>
            Ok(DomainModelResponse(newDomainModel).json)
        }
      }
  }
}
