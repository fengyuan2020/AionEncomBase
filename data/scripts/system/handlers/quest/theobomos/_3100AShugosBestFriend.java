/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 * aion-unique is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-unique is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */

package quest.theobomos;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Balthazar
 */

public class _3100AShugosBestFriend extends QuestHandler {

	private final static int questId = 3100;
	public _3100AShugosBestFriend() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(203792).addOnQuestStart(questId);
		qe.registerQuestNpc(203792).addOnTalkEvent(questId);
		qe.registerQuestNpc(798169).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203792) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						return sendQuestDialog(env, 1011);
					}
					case ASK_ACCEPTION: {
						return sendQuestDialog(env, 4);
					}
					case ACCEPT_QUEST: {
						if (player.getInventory().getItemCountByItemId(182208072) == 0) {
							giveQuestItem(env, 182208072, 1);
						    return sendQuestStartDialog(env);
						}
					}
				}
			}
		}
		if (qs == null)
			return false;
		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 798168: {
					switch (env.getDialog()) {
						case START_DIALOG: {
							if (qs.getQuestVarById(0) == 0) {
								return sendQuestDialog(env, 1352);
							}
						}
						case STEP_TO_1: {
							removeQuestItem(env, 182208072, 1);
							qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
				            return closeDialogWindow(env);
						}
					}
				}
			}
		}
		else if (qs == null || qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 798169) {
				if (env.getDialogId() == 1009)
					return sendQuestDialog(env, 5);
				else
					return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}