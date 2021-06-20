package org.om.onestepjob.model


data class Job(
    var id: Int,
    var title: String,
    var company: String,
    var location: String,
    var ctc: String,
    var experience: String,
    var saved: Boolean = false,
    var applied: Boolean = false,
    var seen: Boolean = false
)