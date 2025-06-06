/*
 * This file is part of Encom.
 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package ai.instance.cradleOfEternity;

import ai.GeneralNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author (Encom)
/****/

@AIName("Sleeping_Sylfae_Queen")
public class Sleeping_Sylfae_QueenAI2 extends GeneralNpcAI2 {

	@Override
	protected void handleDialogStart(Player player) {
		if (player.isArchDaeva()) {
		    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1352));
		} else {
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
		}
	}
	
	@Override
	public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
		QuestEnv env = new QuestEnv(getOwner(), player, questId, dialogId);
		env.setExtendedRewardIndex(extendedRewardIndex);
		PlayerEffectController effectController = player.getEffectController();
		if (QuestEngine.getInstance().onDialog(env) && dialogId != 1352) {
			return true;
		} if (dialogId == 10000) {
			switch (getNpcId()) {
			    case 834039: //Sleeping Sylfae Queen.
					Npc npc = (Npc) env.getVisibleObject();
					npc.getController().onDelete();
				    QuestService.addNewSpawn(301550000, player.getInstanceId(), 834122, player.getX() + 2, player.getY() + 2, player.getZ() + 1, (byte) 0);
					effectController.removeEffect(21340); //Sylfae Queens Blessing.
					effectController.removeEffect(21344); //Beguiling Visions.
			    break;
			}
		} else if (dialogId == 1011 && questId != 0) {
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), dialogId, questId));
		}
		return true;
	}
}