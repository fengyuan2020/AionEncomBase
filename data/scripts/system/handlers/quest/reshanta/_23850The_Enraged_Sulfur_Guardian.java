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
package quest.reshanta;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Ghostfur & Unknown (Aion-Unique)
/****/

public class _23850The_Enraged_Sulfur_Guardian extends QuestHandler {

    private final static int questId = 23850;
	private final static int[] Ab1141BossA = {264501, 264511};
    public _23850The_Enraged_Sulfur_Guardian() {
        super(questId);
    }
	
    public void register() {
        qe.registerQuestNpc(806520).addOnQuestStart(questId); //Mihobork.
        qe.registerQuestNpc(806520).addOnTalkEvent(questId); //Mihobork.
		qe.registerQuestNpc(806520).addOnAtDistanceEvent(questId); //Mihobork.
		for (int mob: Ab1141BossA) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
    }
	
    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
		int targetId = env.getTargetId();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 806520) { //Mihobork.
                if (env.getDialog() == QuestDialog.START_DIALOG) {
                    return sendQuestDialog(env, 10002);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
        return false;
    }
	
	@Override
	public boolean onAtDistanceEvent(QuestEnv env) {
		final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			QuestService.startQuest(env);
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
			return true;
		}
		return false;
	}
	
    public boolean onKillEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            switch (env.getTargetId()) {
				case 264501:
				case 264511:
                if (qs.getQuestVarById(1) < 1) {
					qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
					updateQuestStatus(env);
				} if (qs.getQuestVarById(1) >= 1) {
					qs.setQuestVarById(0, 1);
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);
				}
            }
        }
        return false;
    }
}