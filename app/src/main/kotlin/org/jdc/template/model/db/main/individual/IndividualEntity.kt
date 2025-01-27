package org.jdc.template.model.db.main.individual

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jdc.template.model.domain.inline.Email
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.HouseholdId
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.domain.inline.Phone
import org.jdc.template.model.domain.type.IndividualType
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

@Entity("Individual")
data class IndividualEntity(
    @PrimaryKey
    val id: IndividualId,
    val householdId: HouseholdId?,
    val individualType: IndividualType,
    val firstName: FirstName?,
    val lastName: LastName?,
    val birthDate: LocalDate?,
    val alarmTime: LocalTime?,
    val phone: Phone?,
    val email: Email?,
    val available: Boolean,

    val created: OffsetDateTime,
    val lastModified: OffsetDateTime
)
