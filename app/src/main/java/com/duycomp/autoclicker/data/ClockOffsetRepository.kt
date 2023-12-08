package com.duycomp.autoclicker.data

import com.duycomp.autoclicker.common.result.Result
import com.duycomp.autoclicker.network.getClockOffsetTimeIO
import com.duycomp.autoclicker.network.getClockOffsetWorldClock

class ClockOffsetRepository {
    suspend fun getClockOffset(curOffset: Long = 0L): Result<Long> {
        var result = getClockOffsetTimeIO(curOffset)
        if (result is Result.Success) return result

        result = getClockOffsetWorldClock(curOffset)
        return result
    }
}