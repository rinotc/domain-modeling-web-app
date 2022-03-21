package dev.tchiba.sdmt.infra.domainmodel

import dev.tchiba.sdmt.core.models.domainmodel.{DomainModelId, DomainModelValidator}
import dev.tchiba.sdmt.core.models.project.ProjectId
import scalikejdbc.{DB, SQLInterpolation}

class JdbcDomainModelValidator extends DomainModelValidator with SQLInterpolation {

  /**
   * プロジェクト内に自分自身を除いて同じ英語名が存在するかを調べる
   *
   * @param englishName 存在チェックをしたい英語名
   * @param projectId   プロジェクトID
   * @param selfId      モデル自身のID
   */
  override def isSameEnglishNameModelAlreadyExist(
      englishName: String,
      projectId: ProjectId,
      selfId: DomainModelId
  ): Boolean = DB readOnly { implicit session =>
    selfId.asString
    sql"""
        select * from main.public."domain_models"
        where project_id = ${projectId.asString}
        and english_name = $englishName
        and domain_model_id != ${selfId.asString}
       """
      .map { rs => DomainModelId.fromString(rs.string("domain_model_id")) }
      .single()
      .apply()
      .isDefined
  }
}
