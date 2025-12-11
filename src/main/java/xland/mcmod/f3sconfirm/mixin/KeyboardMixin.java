package xland.mcmod.f3sconfirm.mixin;

import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xland.mcmod.f3sconfirm.F3SConfirmClient;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

    @Inject(method = {"processF3", "method_1468"}, at = @At("HEAD"), cancellable = true)
    private void cancelF3SObj(InputUtil.Key key, CallbackInfoReturnable<Boolean> cir) {
        try {
            if (key == null) return;
            int code = -1;
            try {
                // try common method names via reflection to be robust across mappings
                java.lang.reflect.Method m = key.getClass().getMethod("getCode");
                code = (Integer) m.invoke(key);
            } catch (NoSuchMethodException ignored) {
                try {
                    java.lang.reflect.Method m2 = key.getClass().getMethod("getKeyCode");
                    code = (Integer) m2.invoke(key);
                } catch (NoSuchMethodException ignored2) {
                    try {
                        java.lang.reflect.Method m3 = key.getClass().getMethod("getKey");
                        Object kobj = m3.invoke(key);
                        if (kobj instanceof Integer) code = (Integer) kobj;
                    } catch (NoSuchMethodException | IllegalAccessException ignored3) {
                    }
                }
            }

            if (code == GLFW.GLFW_KEY_S) {
                F3SConfirmClient.debugError(F3SConfirmClient.getTerminationText());
                cir.setReturnValue(true);
            }
        } catch (Throwable ignored) {
        }
    }
}
