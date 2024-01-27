package crazypants.enderzoo.spawn;

import net.minecraft.entity.EntityList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MobSpawnEventHandler {

    public MobSpawnEventHandler() {

    }

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onCheckSpawn(CheckSpawn evt) {
        if (evt.entityLiving == null) return;

        String name = EntityList.getEntityString(evt.entityLiving);
        if (name == null) return;

        for (ISpawnEntry ent : MobSpawns.instance.getEntries()) {
            if (name.equals(ent.getMobName())) {
                if (!ent.canSpawnInDimension(evt.world)) {
                    evt.setResult(Result.DENY);
                }
            }
        }
    }

}
