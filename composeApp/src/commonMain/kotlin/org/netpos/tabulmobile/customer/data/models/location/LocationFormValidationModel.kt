package org.netpos.tabulmobile.customer.data.models.location

data class LocationFormValidationModel(
    val isAddressValid: Boolean,
    val addressError: String = ""
)
