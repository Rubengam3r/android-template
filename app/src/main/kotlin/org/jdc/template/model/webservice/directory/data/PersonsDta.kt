package org.jdc.template.model.webservice.directory.data

import kotlinx.serialization.Serializable

@Serializable
data class PersonsDta(val individuals: List<PersonDta>)
