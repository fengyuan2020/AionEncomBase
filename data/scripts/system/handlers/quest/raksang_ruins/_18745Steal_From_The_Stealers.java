/*
 * =====================================================================================*
 * This file is part of Aion-Unique (Aion-Unique Home Software Development)             *
 * Aion-Unique Development is a closed Aion Project that use Old Aion Project Base      *
 * Like Aion-Lightning, Aion-Engine, Aion-Core, Aion-Extreme, Aion-NextGen, ArchSoft,   *
 * Aion-Ger, U3J, Encom And other Aion project, All Credit Content                      *
 * That they make is belong to them/Copyright is belong to them. And All new Content    *
 * that Aion-Unique make the copyright is belong to Aion-Unique                         *
 * You may have agreement with Aion-Unique Development, before use this Engine/Source   *
 * You have agree with all of Term of Services agreement with Aion-Unique Development   *
 * =====================================================================================*
 */
package quest.raksang_ruins;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/****/
/** Author Ghostfur & Unknown (Aion-Unique)
/****/

public class _18745Steal_From_The_Stealers extends QuestHandler {

	private static final int questId = 18745;
	public _18745Steal_From_The_Stealers() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(206378).addOnQuestStart(questId);
		qe.registerQuestNpc(206379).addOnQuestStart(questId);
		qe.registerQuestNpc(206380).addOnQuestStart(questId);
		qe.registerQuestNpc(804707).addOnTalkEvent(questId);
		qe.registerQuestNpc(702958).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		QuestDialog dialog = env.getDialog();
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 206378 || targetId == 206379 || targetId == 206380) {
				switch (dialog) {
					case START_DIALOG: {
						return sendQuestDialog(env, 4762);
					}
					case ACCEPT_QUEST:
					case ACCEPT_QUEST_SIMPLE:
						return sendQuestStartDialog(env);
					case REFUSE_QUEST_SIMPLE:
				        return closeDialogWindow(env);
				}
			}
		} else if (targetId == 702958) {
			if (dialog == QuestDialog.USE_OBJECT) {
				return true;
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 804707) {
				switch (dialog) {
					case START_DIALOG: {
						return sendQuestDialog(env, 1011);
					} case CHECK_COLLECTED_ITEMS: {
						return checkQuestItems(env, 0, 0, true, 10000, 10001);
					}
				}
			}
		} else if (qs == null || qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 804707) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}