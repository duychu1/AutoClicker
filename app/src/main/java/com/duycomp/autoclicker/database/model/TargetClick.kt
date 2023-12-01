package com.duycomp.autoclicker.database.model

import com.duycomp.autoclicker.model.Position
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TargetClick (
    var position: Position,
    var intervalClick:Long,
    var durationClick:Long,
)