package org.netpos.tabulmobile.customer.data.remote.repository.create_new_password

import org.netpos.tabulmobile.customer.data.models.create_new_password.payload.CreateNewPasswordPayloadModel
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse.PasswordResetResponse
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface CreateNewPasswordRepositoryInterface {

    suspend fun createNewPassword(
        createNewPasswordPayloadModel: CreateNewPasswordPayloadModel
    ): TabulResult<PasswordResetResponse, ErrorDataTypes.Remote>
}