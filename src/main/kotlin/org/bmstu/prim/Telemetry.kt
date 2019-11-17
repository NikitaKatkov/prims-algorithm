package org.bmstu.prim

import org.apache.log4j.LogManager


object Telemetry {
    private val LOG = LogManager.getLogger(Telemetry::class.java)

    private val groupTimes = mutableMapOf<String, Long>()
    private val groupCurrentPeriods = mutableMapOf<String, Long?>()


    fun initWith(vararg groupIds: String) {
        groupIds.forEach { registerGroup(it) }
    }

    /**
     * Must be called before #startPeriodForGroup with the same groupId
     */
    fun registerGroup(groupId: String) {
        groupTimes[groupId] = 0L
    }

    fun startPeriodForGroup(groupId: String) {
        val now = now()
        if (hasStartedPeriod(groupId)) {
            LOG.warn("Group $groupId has active operation being recorded, overwriting previous state")
        }
        groupCurrentPeriods[groupId] = now
    }

    fun finishPeriodForGroup(groupId: String) {
        val duration = now() - groupCurrentPeriods[groupId].orZero()
        if (!hasStartedPeriod(groupId)) {
            LOG.warn("Group $groupId has no active operation being recorded, ignoring request")
            return
        }
        groupCurrentPeriods[groupId] = null
        groupTimes[groupId] = duration + groupTimes[groupId]!!
    }

    private fun hasStartedPeriod(groupId: String) = groupCurrentPeriods[groupId] != null

    fun getAllTimings() = groupTimes

    fun getPercentage(groupId: String, againstTo: String): Long {
        val currentGroupTime = groupTimes[groupId]
        val overallTime = groupTimes[againstTo]
        if (currentGroupTime == null || overallTime == null) return 0L
        return 100 * currentGroupTime / overallTime
    }

    fun reset() {
        groupTimes.clear()
        groupCurrentPeriods.clear()
    }

    private fun now() = System.currentTimeMillis()
    private fun Long?.orZero() = this ?: 0L
}
