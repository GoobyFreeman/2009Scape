package core.game.world.callback

import core.game.system.SystemLogger.error
import core.game.world.map.zone.ZoneBuilder
import core.game.node.entity.skill.farming.FarmingPulse
import core.game.node.entity.skill.hunter.ImpetuousImpulses
import core.game.world.GameWorld
import core.game.ge.OfferManager
import core.game.node.entity.skill.farming.pot.SeedlingPulse
import java.util.ArrayList

/**
 * Initializes a few world pulses that need to continuously run
 * @author Vexia
 */
object CallbackHub {
    private var calls: MutableList<CallBack> = ArrayList()

    fun call(): Boolean {
        calls.add(ZoneBuilder())
        calls.add(OfferManager())
        calls.add(FarmingPulse())
        calls.add(ImpetuousImpulses())
        GameWorld.Pulser.submit(SeedlingPulse())
        for (call in calls) {
            if (!call.call()) {
                error("A callback was stopped, callback=" + call.javaClass.simpleName + ".")
                return false
            }
        }
        calls.clear()
        return true
    }
}