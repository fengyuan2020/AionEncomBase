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
package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Mr. Poke fix by Nephis and quest helper team.
 * @reworked vlog
 */

public class _2232TheBrokenHoneyJar extends QuestHandler {

	private final static int questId = 2232;
	public _2232TheBrokenHoneyJar() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(203613).addOnQuestStart(questId);
		qe.registerQuestNpc(203613).addOnTalkEvent(questId);
		qe.registerQuestNpc(203622).addOnTalkEvent(questId);
		qe.registerQuestNpc(700061).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203613) { // Gilungk
				if (env.getDialog() == QuestDialog.START_DIALOG) {
					return sendQuestDialog(env, 1011);
				}
				else {
					return sendQuestStartDialog(env);
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (targetId == 203613) { // Gilungk
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 1) {
							return sendQuestDialog(env, 2375);
						}
					}
					case CHECK_COLLECTED_ITEMS: {
						return checkQuestItems(env, 1, 1, true, 5, 2716); // reward
					}
					case FINISH_DIALOG: {
						return sendQuestSelectionDialog(env);
					}
				}
			}
			else if (targetId == 700061) { // Beehive
				if (env.getDialog() == QuestDialog.USE_OBJECT) {
					return true; // loot
				}
			}
			else if (targetId == 203622) { // Tatural
				if (env.getDialog() == QuestDialog.START_DIALOG) {
					if (var == 0) {
						return sendQuestDialog(env, 1352);
					}
				}
				else if (env.getDialog() == QuestDialog.STEP_TO_1) {
					return defaultCloseDialog(env, 0, 1); // 1
				}
			}
		}
		else if (qs == null || qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203613) { // Gilungk
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}