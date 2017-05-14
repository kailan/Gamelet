package pw.kmp.gamelet.modules.objective

import pw.kmp.gamelet.match.participant.Participant

/**
 * Tracks progress towards an objective and handles declaring a winner.
 */
interface ObjectiveModule {

    /**
     * Returns the winner of the match, if any.
     */
    fun getWinner(): Participant?

}