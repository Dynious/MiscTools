package redmennl.mods.mito.entity;

import redmennl.mods.mito.lib.Library;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;

public class EntityCompanion extends EntityTameable
{
	public int health = 20;
	
	public EntityCompanion(World world)
	{
		super(world);
		this.moveSpeed = 0.3F;
		this.setSize(0.8F, 1.0F);
		this.getNavigator().setAvoidsWater(true);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxHealth()
	{
		return 20;
	}
	
	public int getTalkInterval()
	{
		return 1200;
	}
	
    protected float getSoundPitch()
    {
		return 1F;
    	
    }
	
    protected String getHurtSound()
    {
        return Library.SOUND_COMPANION_HURT;
    }
    
    protected String getLivingSound()
    {
        return Library.SOUND_COMPANION_SAY;
    }
    
    protected String getDeathSound()
    {
        return Library.SOUND_COMPANION_DEATH;
    }
}
