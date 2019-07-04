package thefirstchange.example.com.communicationtext.netty.client.handler;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.client.NettyClient;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.AddDongTaiImage_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.AddDongtai_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetDongtaiByDTID_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetDongtaiICByDTID_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetDongtaiUICByDTID_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetNewDongtaiIDs_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetOldDongtaiIDs_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetUDtByDTID_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetUDtICByDTICID_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetUNDtIDs_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler.GetUsODtIDs_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler.GetGroupChatTextNotRead_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler.GetSingleChatTextNotRead_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler.GroupChatFile_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler.GroupChatText_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler.SendGroupChatFile_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler.SendSingleChatFile_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler.SendSingleChatText_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler.SingleChatFile_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.AddCorApPart_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.AlterCorpPart_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.AlterCorpPos_ResonseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.AlterCorpYearTerm_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.AppointCorpPosResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.CorpLoadCourseRs_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.CreateCorpration_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.DeleteCorpPart_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.GetAllSD_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.GetSDOfGid_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.LoadCourseOfCorp_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.SchduleArrangement_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.SearchEmptyCourse_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.SendAdmiDutySche_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.SendAllUsersDutyNotiNO_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.SendUserAlterCorpPart_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.SendUserDeleteCorpPart_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler.UpGroupPart_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.eduSysHandler.GetCourseSchOfUID_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.eduSysHandler.GetScoreOfUID_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.eduSysHandler.SearchEmptyClassroom_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.AddFriend_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.AddGroupReturn_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.AddGroupToGroupList_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.AddGroup_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.AddMeAsFriend_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.AddMeFriendReturn_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.AddUserToFriendList_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.AgreeYourFriendRequest_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.CreateGroup_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.DeleteFriend_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.DisAgreeYourFriendRequest_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.ExitGroup_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.FDeleteMe_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetFriendListUIcByPh_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetGroupAllUser_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetGroupICOfSearchAddGroup_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetGroupIcByGid_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetGroupUserIconByPh_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetRequestFriendOrGroup_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetUICOfSearchAddUser_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetUIcOfAgreeYourFriend_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.GetUserIcOfAddMeAsFriend_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.SearchAddGroup_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.SearchAddUser_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.SendAdmiAddGroupHandlered_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.SendAdmiAddGroup_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.SendExitGroupToAdmi_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.SendUserAddGroupResult_PacketHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.UpFriendRemark_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.UpGroupIcon_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler.UpGroupRemarkt_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.notificationHandler.GetNotiIconOfGroupResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.notificationHandler.GetNotiIconOfUserResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.FreshAllFriendInfo_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.GetAllFriendInfo_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.GetFriendIDOnline_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.GetFriendInfoByID_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.GetGroupInfoByGid_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.GetGroupsInfoOfUser_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.GetIndexICOfPh_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.GetIndexInfoOfPh_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler.GetUserIcOfPh_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler.GetPerIcon_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler.GetPersonalInfo_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler.IsPhRegistedResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler.RegisterTestesponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler.UpPasswordResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler.UpPeronalInfo_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler.UpPersonalIcon_ResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler.loginByOtherPacketHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddCorApPart_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddDongTaiImage_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddDongtai_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddFriend_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddGroupReturn_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddGroupToGroupList_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddGroup_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddMeAsFriend_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddMeFriendReturn_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddUserToFriendList_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AgreeYourFriendRequest_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AlterCorpPart_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AlterCorpPos_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AlterCorpYearTerm_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AppointCorpPos_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.CorpLoadCourseRs_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.CreateCorpration_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.CreateGroup_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.DeleteCorpPart_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.DeleteFriend_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.DisAgreeYourFriendRequest_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ExitGroup_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.FDeleteMe_Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.FreshAllFriendInfo_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetAllFriendInfo_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetAllSD_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetCourseSchOfUID_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDongtaiByDTID_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDongtaiICByDTID_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDongtaiUICByDTID_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetFriendIDOnline_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetFriendInfoByID_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetFriendListUIcByPh_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupAllUser_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupChatTextNotRead_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupICOfSearchAddGroup_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupIcByGid_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupInfoByGid_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupUserIconByPh_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupsInfoOfUser_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetIndexICOfPh_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetIndexInfoOfPh_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetNewDongtaiIDs_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetNotiIconOfGroup_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetNotiIconOfUser_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetOldDongtaiIDs_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetPerIcon_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetPersonalInfo_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetRequestFriendOrGroup_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetSDOfGid_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetScoreOfUID_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetSingleChatTextNotRead_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUDtByDTID_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUDtICByDTICID_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUICOfSearchAddUser_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUIcOfAgreeYourFriend_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUNDtIDs_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUsODtIDs_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUserIcOfAddMeAsFriend_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUserIcOfPh_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GroupAdmiReciveExitGroup_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GroupChatFile_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GroupChatText_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.IsPhRegisted_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.LOGIN_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.LOGOUT_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.LoadCourseOfCorp_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.REGISTERTEST_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SchduleArrangement_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SearchAddGroup_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SearchAddUser_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SearchEmptyClassroom_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SearchEmptyCourse_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendAdmiAddGroupHandlered_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendAdmiAddGroup_Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendAdmiDutySche_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendAllUsersDutyNotiNO_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendExitGroupToAdmi_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendGroupChatFile_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendSingleChatFile_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendUserAddGroupResult_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendUserAlterCorpPart_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendUserDeleteCorpPart_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SingleChatFile_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SingleChatText_PACKET;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UPPASSWORD_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpFriendRemark_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpGroupIcon_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpGroupPart_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpGroupRemarkt_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpPeronalInfo_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpPersonalIcon_RESPONSE;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.loginByOther_Packet;


public class IMHandler extends SimpleChannelInboundHandler<Packet> {


    private Map<Integer, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    public IMHandler() {

        handlerMap = new HashMap<>();

        handlerMap.put(LOGIN_RESPONSE, new LoginResponseHandler());
        handlerMap.put(LOGOUT_RESPONSE, new LogoutResponseHandler());

        handlerMap.put(UPPASSWORD_RESPONSE, new UpPasswordResponseHandler());
        handlerMap.put(REGISTERTEST_RESPONSE, new RegisterTestesponseHandler());
        handlerMap.put(IsPhRegisted_RESPONSE, new IsPhRegistedResponseHandler());

        handlerMap.put(SendGroupChatFile_RESPONSE, new SendGroupChatFile_ResponseHandler());
        handlerMap.put(GroupChatFile_PACKET, new GroupChatFile_PacketHandler());

        handlerMap.put(SingleChatText_PACKET, new SendSingleChatText_PacketHandler());
        handlerMap.put(SingleChatFile_PACKET, new SingleChatFile_PacketHandler());

        handlerMap.put(GroupChatText_PACKET, new GroupChatText_PacketHandler());

//        handlerMap.put(SingleChatText_RESPONSE, new SingleChatText_RESPONSE());
        handlerMap.put(SendSingleChatFile_RESPONSE, new SendSingleChatFile_ResponseHandler());
//        handlerMap.put(SingleChatFile_RESPONSE, new SingleChatFile_RESPONSE());
//        handlerMap.put(ReadSingleChatMsg_RESPONSE, new ReadSingle());
//        handlerMap.put(GetSingleChatMsgNotRead_RESPONSE, new Getsingle());
        handlerMap.put(GetSingleChatTextNotRead_PACKET, new GetSingleChatTextNotRead_PacketHandler());
//        handlerMap.put(GetGroupChatMsgNotRead_RESPONSE, new GetGroupCha());
        handlerMap.put(GetGroupChatTextNotRead_PACKET, new GetGroupChatTextNotRead_PacketHandler());
        handlerMap.put(GetFriendInfoByID_RESPONSE, new GetFriendInfoByID_ResponseHandler());
        handlerMap.put(GetGroupsInfoOfUser_RESPONSE, new GetGroupsInfoOfUser_ResponseHandler());
        handlerMap.put(GetAllFriendInfo_RESPONSE, new GetAllFriendInfo_ResponseHandler());
        handlerMap.put(FreshAllFriendInfo_RESPONSE, new FreshAllFriendInfo_ResponseHandler());
//        handlerMap.put(GetFriendIconOfUID_RESPONSE, new GetFriendIconOfUID_());
//        handlerMap.put(FreshNotification_RESPONSE, new FreshNotificationr());
        handlerMap.put(GetFriendIDOnline_RESPONSE, new GetFriendIDOnline_ResponseHandler());
        handlerMap.put(DeleteFriend_RESPONSE, new DeleteFriend_ResponseHandler());
        handlerMap.put(FDeleteMe_Packet, new FDeleteMe_PacketHandler());
//        handlerMap.put(ReciveFDeleteMe_RESPONSE, new ReciveFDeleteme());
        handlerMap.put(ExitGroup_RESPONSE, new ExitGroup_ResponseHandler());
        handlerMap.put(SendExitGroupToAdmi_PACKET, new SendExitGroupToAdmi_PacketHandler());
        handlerMap.put(GroupAdmiReciveExitGroup_RESPONSE, new SendExitGroupToAdmi_PacketHandler());
//        handlerMap.put(UpRequestMsgState_RESPONSE, new UpRequestMsgStateRes());
        handlerMap.put(AddFriend_RESPONSE, new AddFriend_ResponseHandler());



        handlerMap.put(AddMeAsFriend_PACKET, new AddMeAsFriend_PacketHandler());
        handlerMap.put(AddMeFriendReturn_RESPONSE, new AddMeFriendReturn_ResponseHandler());
        handlerMap.put(AgreeYourFriendRequest_PACKET, new AgreeYourFriendRequest_PacketHandler());
        handlerMap.put(DisAgreeYourFriendRequest_PACKET, new DisAgreeYourFriendRequest_PacketHandler());
//        handlerMap.put(AddFriendResult_RESPONSE, new AddFriendResult_Res());
        handlerMap.put(AddGroup_RESPONSE, new AddGroup_ResponseHandler());
        handlerMap.put(SendAdmiAddGroup_Packet, new SendAdmiAddGroup_PacketHandler());
        handlerMap.put(AddGroupReturn_RESPONSE, new AddGroupReturn_ResponseHandler());
        handlerMap.put(SendUserAddGroupResult_PACKET, new SendUserAddGroupResult_PacketHandler());
//        handlerMap.put(SendAdmiAddGroupHandlered_PACKET, new SendAdmiAddGroupHandlered());
//        handlerMap.put(ReadAddGroupResult_RESPONSE, new ReadAddGroupResultr());
        handlerMap.put(GetRequestFriendOrGroup_RESPONSE, new GetRequestFriendOrGroup_ResponseHandler());
        handlerMap.put(GetPersonalInfo_RESPONSE, new GetPersonalInfo_ResponseHandler());
        handlerMap.put(SearchAddUser_RESPONSE, new SearchAddUser_ResponseHandler());
        handlerMap.put(SearchAddGroup_RESPONSE, new SearchAddGroup_ResponseHandler());
        handlerMap.put(UpPersonalIcon_RESPONSE, new UpPersonalIcon_ResponseHandler());
        handlerMap.put(GetPerIcon_RESPONSE, new GetPerIcon_ResponseHandler());
        handlerMap.put(UpPeronalInfo_RESPONSE, new UpPeronalInfo_ResponseHandler());
        handlerMap.put(UpFriendRemark_RESPONSE, new UpFriendRemark_ResponseHandler());
        handlerMap.put(AddUserToFriendList_RESPONSE, new AddUserToFriendList_ResponseHandler());
        handlerMap.put(UpGroupRemarkt_RESPONSE, new UpGroupRemarkt_ResponseHandler());
        handlerMap.put(UpGroupPart_RESPONSE, new UpGroupPart_ResponseHandler());
        handlerMap.put(GetGroupAllUser_RESPONSE, new GetGroupAllUser_ResponseHandler());
        handlerMap.put(GetGroupUserIconByPh_RESPONSE, new GetGroupUserIconByPh_ResponseHandler());
        handlerMap.put(CreateCorpration_RESPONSE, new CreateCorpration_ResponseHandler());
        handlerMap.put(CreateGroup_RESPONSE, new CreateGroup_ResponseHandler());
        handlerMap.put(AddGroupToGroupList_RESPONSE, new AddGroupToGroupList_ResponseHandler());
        handlerMap.put(GetCourseSchOfUID_RESPONSE, new GetCourseSchOfUID_ResponseHandler());
        handlerMap.put(GetScoreOfUID_RESPONSE, new GetScoreOfUID_ResponseHandler());
        handlerMap.put(LoadCourseOfCorp_RESPONSE, new LoadCourseOfCorp_ResponseHandler());
        handlerMap.put(SchduleArrangement_RESPONSE, new SchduleArrangement_ResponseHandler());
        handlerMap.put(SendAdmiDutySche_PACKET, new SendAdmiDutySche_PacketHandler());
        handlerMap.put(SendAllUsersDutyNotiNO_PACKET, new SendAllUsersDutyNotiNO_PacketHandler());
        handlerMap.put(GetSDOfGid_RESPONSE, new GetSDOfGid_ResponseHandler());
        handlerMap.put(GetAllSD_RESPONSE, new GetAllSD_ResponseHandler());
//        handlerMap.put(GetAllDutyNotiNotRead_RESPONSE, new GetAllDutyNotiNotReadResponsePacket());
//        handlerMap.put(ReadDutyNoti_RESPONSE, new ReadDutyNoti_R());
        handlerMap.put(SearchEmptyCourse_RESPONSE, new SearchEmptyCourse_ResponseHandler());
        handlerMap.put(CorpLoadCourseRs_RESPONSE, new CorpLoadCourseRs_ResponseHandler());
        handlerMap.put(AlterCorpYearTerm_RESPONSE, new AlterCorpYearTerm_ResponseHandler());
        handlerMap.put(AddCorApPart_RESPONSE, new AddCorApPart_ResponseHandler());
        handlerMap.put(DeleteCorpPart_RESPONSE, new DeleteCorpPart_ResponseHandler());
        handlerMap.put(SendUserDeleteCorpPart_PACKET, new SendUserDeleteCorpPart_PacketHandler());
        handlerMap.put(AlterCorpPart_RESPONSE, new AlterCorpPart_ResponseHandler());
        handlerMap.put(SendUserAlterCorpPart_PACKET, new SendUserAlterCorpPart_PacketHandler());
        handlerMap.put(AlterCorpPos_RESPONSE, new AlterCorpPos_ResonseHandler());

        handlerMap.put(GetUDtByDTID_RESPONSE, new GetUDtByDTID_ResponseHandler());
        handlerMap.put(GetUDtICByDTICID_RESPONSE, new GetUDtICByDTICID_ResponseHandler());
        handlerMap.put(GetUNDtIDs_RESPONSE, new GetUNDtIDs_ResponseHandler());
        handlerMap.put(GetUsODtIDs_RESPONSE, new GetUsODtIDs_ResponseHandler());
        handlerMap.put(GetDongtaiByDTID_RESPONSE, new GetDongtaiByDTID_ResponseHandler());
        handlerMap.put(GetDongtaiUICByDTID_RESPONSE, new GetDongtaiUICByDTID_ResponseHandler());
        handlerMap.put(GetDongtaiICByDTID_RESPONSE, new GetDongtaiICByDTID_ResponseHandler());
        handlerMap.put(GetNewDongtaiIDs_RESPONSE, new GetNewDongtaiIDs_ResponseHandler());
        handlerMap.put(GetOldDongtaiIDs_RESPONSE, new GetOldDongtaiIDs_ResponseHandler());
        handlerMap.put(AddDongtai_RESPONSE, new AddDongtai_ResponseHandler());
        handlerMap.put(AddDongTaiImage_RESPONSE, new AddDongTaiImage_ResponseHandler());

        //进入某人的个人页面时  获得其基本信息
        handlerMap.put(GetIndexInfoOfPh_RESPONSE, new GetIndexInfoOfPh_ResponseHandler());
        //进入某人的个人页面时  获得其头像
        handlerMap.put(GetIndexICOfPh_RESPONSE, new GetIndexICOfPh_ResponseHandler());

        //社团组织任命职位
        handlerMap.put(AppointCorpPos_RESPONSE,new AppointCorpPosResponseHandler());

        //	添加好友  被添加方要获得对方的头像
        handlerMap.put(GetUserIcOfAddMeAsFriend_RESPONSE, new GetUserIcOfAddMeAsFriend_ResponseHandler());

        //对方同意了你的好友请求    获得对方的头像
        handlerMap.put(GetUIcOfAgreeYourFriend_RESPONSE, new GetUIcOfAgreeYourFriend_ResponseHandler());

        //添加好友时    首先查询     获得对方的头像
        handlerMap.put(GetUICOfSearchAddUser_RESPONSE,new GetUICOfSearchAddUser_ResponseHandler());


        //更改群头像
        handlerMap.put(UpGroupIcon_RESPONSE,new UpGroupIcon_ResponseHandler());
        //获得某群头像
        handlerMap.put(GetGroupIcByGid_RESPONSE,new GetGroupIcByGid_ResponseHandler());

        //获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        handlerMap.put(GetNotiIconOfGroup_RESPONSE,new GetNotiIconOfGroupResponseHandler());

        //获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        handlerMap.put(GetNotiIconOfUser_RESPONSE,new GetNotiIconOfUserResponseHandler());

        //获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        handlerMap.put(GetFriendListUIcByPh_RESPONSE,new GetFriendListUIcByPh_ResponseHandler());

        //获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        handlerMap.put(GetGroupICOfSearchAddGroup_RESPONSE,new GetGroupICOfSearchAddGroup_ResponseHandler());

        //通知其他管理员 加群请求已经被其他管理员处理了
        handlerMap.put(SendAdmiAddGroupHandlered_PACKET,new SendAdmiAddGroupHandlered_PacketHandler());

        //获得某用户头像
        handlerMap.put(GetUserIcOfPh_RESPONSE,new GetUserIcOfPh_ResponseHandler());

        //获得用户的某群的信息   一般用在更新某群信息时
        handlerMap.put(GetGroupInfoByGid_RESPONSE,new GetGroupInfoByGid_ResponseHandler());

        //获得用户的某群的信息   一般用在更新某群信息时
        handlerMap.put(SearchEmptyClassroom_RESPONSE,new SearchEmptyClassroom_ResponseHandler());

        //自己登陆   有人异地登陆  顶替自己
        handlerMap.put(loginByOther_Packet,new loginByOtherPacketHandler());


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettyClient.getInstance().setConnectStatus(false);
    }


}
