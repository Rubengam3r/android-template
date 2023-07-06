package org.jdc.template.model.webservice.directory.data

import kotlinx.serialization.Serializable

@Serializable
data class PersonDta(
        var id: String,
        var firstName: String,
        var lastName: String,
        var birthDate: String,
        var profilePicture: String,
        var forceSensitive: Boolean,
        var affiliation: String,
        var lastModified: String

)
