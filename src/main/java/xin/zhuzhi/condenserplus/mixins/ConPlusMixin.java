package xin.zhuzhi.condenserplus.mixins;

import appeng.api.stacks.AEFluidKey;
import appeng.blockentity.misc.CondenserBlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(targets = "appeng.blockentity.misc.CondenserBlockEntity$FluidHandler")
public class ConPlusMixin {

    @Final
    @Shadow
    CondenserBlockEntity this$0;

    /**
     * @author XzZZzX02
     * @reason make condenser as fast as before
     */
    @Overwrite
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        int amount = resource.isEmpty() ? 0 : resource.getAmount();

        if (action == IFluidHandler.FluidAction.EXECUTE) {
            var what = AEFluidKey.of(resource);
            if (what != null) {
                var transferFactor = (double) what.getAmountPerOperation();
                this$0.addPower(amount / transferFactor);
            }
        }

        return amount;
    }

}
