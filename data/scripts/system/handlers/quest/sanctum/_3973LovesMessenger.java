/*

 *
 *  Encom is free software: you can redistribute it and/or modif (y
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
package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/****/
/** Author Ghostfur & Unknown (Aion-Unique)
/****/
public class _3973LovesMessenger extends QuestHandler {

	private final static int questId = 3973;
	public _3973LovesMessenger() {
		super(questId);
	}
	
	public void register() {
		qe.registerQuestNpc(203893).addOnQuestStart(questId);
		qe.registerQuestNpc(203893).addOnTalkEvent(questId);
		qe.registerQuestNpc(203792).addOnTalkEvent(questId);
		qe.registerQuestNpc(203793).addOnTalkEvent(questId);
		qe.registerQuestNpc(798391).addOnTalkEvent(questId);
		qe.registerQuestNpc(798949).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		QuestDialog dialog = env.getDialog();
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203893) { 
				if (dialog == QuestDialog.START_DIALOG) {
					return sendQuestDialog(env, 1011);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 203792) {
				if (dialog == QuestDialog.START_DIALOG) {
					if(qs.getQuestVarById(0) == 0) {
						return sendQuestDialog(env, 1352);
					}
				} else if (dialog == QuestDialog.STEP_TO_1) {
					return defaultCloseDialog(env, 0, 1);
				}
			} if (targetId == 203793) {
				if (dialog == QuestDialog.START_DIALOG) {
					if(qs.getQuestVarById(0) == 1) {
						return sendQuestDialog(env, 1693);
					}
				} else if (dialog == QuestDialog.STEP_TO_2) {
					return defaultCloseDialog(env, 1, 2);
				}
			} if (targetId == 798391) {
				if (dialog == QuestDialog.START_DIALOG) {
					if(qs.getQuestVarById(0) == 2) {
						return sendQuestDialog(env, 2034);
					}
				} else if (dialog == QuestDialog.STEP_TO_3) {
					return defaultCloseDialog(env, 2, 3);
				}
			} if (targetId == 798949) {
				if (dialog == QuestDialog.START_DIALOG) {
					if(qs.getQuestVarById(0) == 3) {
						return sendQuestDialog(env, 2375);
					}
				} else if (dialog == QuestDialog.SELECT_REWARD) {
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);
					return sendQuestEndDialog(env);
				}
			}
		} else if (qs == null || qs.getStatus() == QuestStatus.REWARD) {
				return sendQuestEndDialog(env);
		}
		return false;
	}
}