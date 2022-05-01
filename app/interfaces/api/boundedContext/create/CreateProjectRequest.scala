package interfaces.api.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, BoundedContextName, ProjectOverview}
import dev.tchiba.sdmt.usecase.boundedContext.create.CreateProjectInput
import interfaces.json.{JsonRequest, JsonValidator}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{BodyParser, PlayBodyParsers}

import scala.concurrent.ExecutionContext

case class CreateProjectRequest(
    private val name: String,
    private val alias: String,
    private val overview: String
) extends JsonRequest {
  private val projectName: BoundedContextName   = BoundedContextName.validate(name).leftThrow
  private val projectAlias: BoundedContextAlias = BoundedContextAlias.validate(alias).leftThrow
  private val projectOverview: ProjectOverview  = ProjectOverview.validate(overview).leftThrow

  val input: CreateProjectInput = CreateProjectInput(projectAlias, projectName, projectOverview)
}

object CreateProjectRequest {
  implicit val jsonFormat: OFormat[CreateProjectRequest] = Json.format[CreateProjectRequest]

  def validateJson(implicit parse: PlayBodyParsers, ec: ExecutionContext): BodyParser[CreateProjectRequest] =
    JsonValidator.validate
}
