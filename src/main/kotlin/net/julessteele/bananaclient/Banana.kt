package net.julessteele.bananaclient

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Banana: ModInitializer {

    const val NAME: String = "Banana Client"
    const val MOD_ID: String = "bananaclient"
    val logger: Logger = LoggerFactory.getLogger(MOD_ID)

    // Resources not initialized yet... In a mod load ready state
	override fun onInitialize() {
		logger.info("Hello from the {} Mod Initializer!", NAME)
	}
}
