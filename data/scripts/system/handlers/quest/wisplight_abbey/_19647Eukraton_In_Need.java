/*

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
package quest.wisplight_abbey;

import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.questEngine.handlers.*;
import com.aionemu.gameserver.questEngine.model.*;
import com.aionemu.gameserver.utils.*;

/****/
/** Author Rinzler (Encom)
/****/

public class _19647Eukraton_In_Need extends QuestHandler {

	private final static int questId = 19647;
	private final static int[] mobs = {235852, 235853, 235856, 235857, 235869, 235870};
	public _19647Eukraton_In_Need() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(804651).addOnQuestStart(questId);
		qe.registerQuestNpc(804651).addOnTalkEvent(questId);
		qe.registerQuestNpc(804704).addOnTalkEvent(questId); //LF5 Eukraton.
		for (int mob: mobs) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		QuestDialog dialog = env.getDialog();
		int targetId = env.getTargetId();
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		} if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			if (targetId == 804651) {
				switch (dialog) {
					case START_DIALOG: {
						if (player.getInventory().getItemCountByItemId(164000335) >= 1) { //Abbey Return Stone.
						    return sendQuestDialog(env, 4762);
						} else {
							PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "You must have <Abbey Return Stone>", ChatType.BRIGHT_YELLOW_CENTER), true);
							return true;
						}
					}
					case ACCEPT_QUEST:
					case ACCEPT_QUEST_SIMPLE:
						return sendQuestStartDialog(env);
					case REFUSE_QUEST_SIMPLE:
				        return closeDialogWindow(env);
				}
			}
		}
        else if (qs.getStatus() == QuestStatus.REWARD) {
		    if (targetId == 804704) { //LF5 Eukraton.
			    switch (dialog) {
					case SELECT_REWARD: {
						return sendQuestDialog(env, 5);
					} default:
						return sendQuestEndDialog(env);
				}
		    }
		}
		return false;
	}
	
	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.START) {
			return false;
		} switch (targetId) {
           	case 235852: 
          	case 235853:
			case 235856:
			case 235857:
			case 235869:
			case 235870:
				if (qs.getQuestVarById(1) < 10) {
					qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
					updateQuestStatus(env);
				} if (qs.getQuestVarById(1) >= 10) {
					qs.setQuestVarById(0, 1);
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);
				}
			break;
		}
		return false;
	}
}