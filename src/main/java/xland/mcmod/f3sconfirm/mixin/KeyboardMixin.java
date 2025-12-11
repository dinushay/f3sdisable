package xland.mcmod.f3sconfirm.mixin;

import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xland.mcmod.f3sconfirm.F3SConfirmClient;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

        @Inject(method = "processF3", at = @At("HEAD"), cancellable = true)
    private void cancelF3S(int key, CallbackInfoReturnable<Boolean> cir) {
        if (key == GLFW.GLFW_KEY_S) {   // another check
            F3SConfirmClient.debugError(F3SConfirmClient.getTerminationText());
            cir.setReturnValue(true);
        }
    }
}
