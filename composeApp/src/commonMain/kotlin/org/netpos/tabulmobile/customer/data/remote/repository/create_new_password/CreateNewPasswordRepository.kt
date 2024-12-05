package org.netpos.tabulmobile.customer.data.remote.repository.create_new_password

import org.netpos.tabulmobile.customer.data.models.create_new_password.payload.CreateNewPasswordPayloadModel
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse.PasswordResetResponse
import org.netpos.tabulmobile.customer.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult
import org.netpos.tabulmobile.customer.domain.remote.map

class CreateNewPasswordRepository(
    private val remoteDataSource: RemoteDataSource
) : CreateNewPasswordRepositoryInterface {
    override suspend fun createNewPassword(
        createNewPasswordPayloadModel: CreateNewPasswordPayloadModel
    ): TabulResult<PasswordResetResponse, ErrorDataTypes.Remote> {
        return remoteDataSource.createNewPassword(createNewPasswordPayloadModel = createNewPasswordPayloadModel)
            .map { it }
    }
}