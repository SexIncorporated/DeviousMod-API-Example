package com.github.allinkdev.deviousmod_api_example;

import com.github.allinkdev.deviousmod.api.DeviousModSilhouette;
import com.github.allinkdev.deviousmod.api.event.impl.keybind.KeyBindLifecycleTransitionEvent;
import com.github.allinkdev.deviousmod.api.event.impl.module.ModuleLifecycleTransitionEvent;
import com.github.allinkdev.deviousmod.api.keybind.KeyBind;
import com.github.allinkdev.deviousmod.api.keybind.KeyBindLifecycle;
import com.github.allinkdev.deviousmod.api.load.DeviousModEntrypoint;
import com.github.allinkdev.deviousmod.api.managers.EventManager;
import com.github.allinkdev.deviousmod.api.module.Module;
import com.github.allinkdev.deviousmod.api.module.ModuleLifecycle;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.option.KeyBinding;

public class ExampleDeviousModEntrypoint implements DeviousModEntrypoint {
    @Override
    public void onLoad(final DeviousModSilhouette deviousMod) {
        ExampleMod.LOGGER.info("DeviousMod load");
    }

    @Override
    public void onPreLoad(final DeviousModSilhouette deviousMod) {
        ExampleMod.LOGGER.info("DeviousMod pre-load");

        /*
            As onLoad() is called after all the managers are fully initialized, registering our listener in onPreLoad() will allow us to receive
            transitions from NONE -> REGISTERED for keybinds & modules
         */

        final EventManager<?> eventManager = deviousMod.getEventManager();
        eventManager.registerListener(this);
    }

    @Subscribe
    private void onModuleLifecycleTransition(final ModuleLifecycleTransitionEvent event) {
        final Module module = event.getTracked();
        final ModuleLifecycle from = event.getFrom();
        final ModuleLifecycle to = event.getTo();

        ExampleMod.LOGGER.info("Module {} has transitioned from lifecycle {} to {}!", module.getModuleName(), from.name(), to.name());
    }

    @Subscribe
    private void onKeybindLifecycleTransition(final KeyBindLifecycleTransitionEvent<KeyBinding> event) {
        final KeyBind<KeyBinding> keybind = event.getTracked();
        final KeyBindLifecycle from = event.getFrom();
        final KeyBindLifecycle to = event.getTo();

        ExampleMod.LOGGER.info("Module {} has transitioned from lifecycle {} to {}!", keybind.getName(), from.name(), to.name());
    }
}
