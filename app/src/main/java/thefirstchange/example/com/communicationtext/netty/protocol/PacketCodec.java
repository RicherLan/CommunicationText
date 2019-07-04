package thefirstchange.example.com.communicationtext.netty.protocol;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.AddMeAsFriend_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.AgreeYourFriendRequest_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.DisAgreeYourFriendRequest_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.FDeleteMe_Packet;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.GetGroupChatTextNotRead_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.GetSingleChatTextNotRead_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.GroupChatFile_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.GroupChatText_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.LoginByOther_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendAdmiAddGroupHandlered_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendAdmiAddGroup_Packet;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendAdmiDutySche_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendAllUsersDutyNotiNO_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendExitGroupToAdmi_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendUserAddGroupResult_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendUserAlterCorpPart_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendUserDeleteCorpPart_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SingleChatFile_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SingleChatText_PACKET;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.AddCorApPartRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.AlterCorpPartRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.AlterCorpPosRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.AppointCorpPosRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.DeleteCorpPartRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.AddDongTaiImageRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.AddDongtaiRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDongtaiByDTIDRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDongtaiICByDTIDRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDongtaiUICByDTID_RequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetNewDongtaiIDsRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetOldDongtaiIDsRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetUDtByDTIDRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetUDtICByDTICIDRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetUNDtIDsRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetUsODtIDsRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.edu.SearchEmptyClassroomRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetFriendListUIcByPhRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetGroupICOfSearchAddGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetGroupIcByGidRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetGroupInfoByGidRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetUICOfSearchAddUserRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetUIcOfAgreeYourFriendRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetUserIcOfAddGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetUserIcOfAddMeAsFriendRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.UpGroupIconRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.notificationRequest.GetNotiIconOfGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.notificationRequest.GetNotiIconOfUserRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddFriendRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddFriendResultRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddGroupReturnRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddGroupToGroupListRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddMeFriendReturnRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddUserToFriendListRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AlterCorpYearTermRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.CorpLoadCourseRsRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.CreateCorprationRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.CreateGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.DeleteFriendRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ExitGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.FreshAllFriendInfoRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetAllDutyNotiNotReadRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetAllFriendInfoRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetAllSDRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetCourseSchByUIDRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetFriendIDOnlineRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetFriendInfoByIDRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetGroupAllUserRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetGroupChatMsgNotReadRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetGroupUserIconByPhRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetGroupsInfoOfUserRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetPerIconRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetPersonalInfoRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetRequestFriendOrGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetSDOfGidRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetScoreOfUIDRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetSingleChatMsgNotReadRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GroupAdmiReciveExitGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.HeartBeatRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.IsPhonenumberRegistedRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.LoadCourseOfCorpRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.LoginRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.LogoutRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReadDutyNotiRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReadGroupChatMsgRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReadSingleChatMsgRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReadaddGroupResultRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReciveFDeleteMeRequestPAcket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.RegisterTestRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SchduleArrangementRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SearchAddGroupRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SearchAddUserRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SearchEmptyCourseRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SendGroupChatFileRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SendGroupChatTextRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SendSingleChatFileRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SendSingleChatTextRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpFriendRemarkRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpGroupPartRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpGroupRemarkRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpPasswordRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpPeronalInfoRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpPersonalIconRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpRequestMsgStateRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.personInfoRequest.GetIndexICOfPhRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.personInfoRequest.GetIndexInfoOfPhRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.request.userOtherInfoRequest.GetUserIcOfPhRequestPacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.AddCorApPartResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.AlterCorpPartResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.AlterCorpPosResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.AppointCorpPosResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.DeleteCorpPartResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.AddDongTaiImageResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.AddDongtaiResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetDongtaiByDTIDResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetDongtaiICByDTID_ResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetDongtaiUICByDTIDResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetNewDongtaiIDsResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetOldDongtaiIDsResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetUDtByDTIDResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetUDtICByDTICIDResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetUNDtIDsResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetUsODtIDsResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.edu.SearchEmptyClassroomResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetFriendListUIcByPhResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetGroupICOfSearchAddGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetGroupIcByGidResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetGroupInfoByGidResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetUICOfSearchAddUserResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetUIcOfAgreeYourFriendResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetUserIcOfAddGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetUserIcOfAddMeAsFriendResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.UpGroupIconResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.notificationResponse.GetNotiIconOfGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.notificationResponse.GetNotiIconOfUserResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddFriendResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddFriendResultResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddGroupReturnResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddGroupToGroupListResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddMeFriendReturnResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddUserToFriendListResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AlterCorpYearTermResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.CorpLoadCourseRsResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.CreateCorprationResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.CreateGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.DeleteFriendResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.ExitGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.FreshAllFriendInfoResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetAllDutyNotiNotReadResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetAllFriendInfoResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetAllSDResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetCourseSchByUIDResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetFriendIDOnlineResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetFriendInfoByIDResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetGroupAllUserResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetGroupChatMsgNotReadResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetGroupUserIconByPhResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetGroupsInfoOfUserResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetPerIconResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetPersonalInfoResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetRequestFriendOrGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetSDOfGidResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetScoreOfUIDResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetSingleChatMsgNotReadResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GroupAdmiReciveExitGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.HeartBeatResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.IsPhonenumberRegistedResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.LoadCourseOfCorpResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.LoginResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.LogoutResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.ReADGroupChatMsgResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.ReadDutyNotiResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.ReadaddGroupResultResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.ReciveFDeleteMeReponsePAcket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.RegisterTestResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SchduleArrangementResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SearchAddGroupResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SearchAddUserResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SearchEmptyCourseResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SendGroupChatFileResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SendGroupChatTextResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SendSingleChatFileResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SendSingleChatTextResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpFriendRemarkResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpGroupPartResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpGroupRemarkResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpPasswordResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpPeronalInfoReponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpPersonalIconResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpRequestMsgStateResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.personInfoResponse.GetIndexICOfPhResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.personInfoResponse.GetIndexInfoOfPhResponsePacket;
import thefirstchange.example.com.communicationtext.netty.protocol.response.userOtherInfoResponse.GetUserIcOfPhResponsePacket;
import thefirstchange.example.com.communicationtext.netty.serialize.Serializer;
import thefirstchange.example.com.communicationtext.netty.serialize.impl.JSONSerializer;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.*;


public class PacketCodec {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Integer, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);

        packetTypeMap.put(UPPASSWORD_REQUEST, UpPasswordRequestPacket.class);
        packetTypeMap.put(UPPASSWORD_RESPONSE, UpPasswordResponsePacket.class);

        packetTypeMap.put(REGISTERTEST_REQUEST, RegisterTestRequestPacket.class);
        packetTypeMap.put(REGISTERTEST_RESPONSE, RegisterTestResponsePacket.class);

        packetTypeMap.put(IsPhRegisted_REQUEST, IsPhonenumberRegistedRequestPacket.class);
        packetTypeMap.put(IsPhRegisted_RESPONSE, IsPhonenumberRegistedResponsePacket.class);

        packetTypeMap.put(SendGroupChatText_REQUEST, SendGroupChatTextRequestPacket.class);
        packetTypeMap.put(SendGroupChatText_RESPONSE, SendGroupChatTextResponsePacket.class);

        packetTypeMap.put(GroupChatText_PACKET, GroupChatText_PACKET.class);
//        packetTypeMap.put(GroupChatText_RESPONSE, GroupChatText.class);

        packetTypeMap.put(SendGroupChatFile_REQUEST, SendGroupChatFileRequestPacket.class);
        packetTypeMap.put(SendGroupChatFile_RESPONSE, SendGroupChatFileResponsePacket.class);

        packetTypeMap.put(GroupChatFile_PACKET, GroupChatFile_PACKET.class);
//        packetTypeMap.put(GroupChatFile_RESPONSE, LoginResponsePacket.class);


        packetTypeMap.put(ReadGroupChatMsg_REQUEST, ReadGroupChatMsgRequestPacket.class);
        packetTypeMap.put(ReadGroupChatMsg_RESPONSE, ReADGroupChatMsgResponsePacket.class);

        packetTypeMap.put(SendSingleChatText_REQUEST, SendSingleChatTextRequestPacket.class);
        packetTypeMap.put(SendSingleChatText_RESPONSE, SendSingleChatTextResponsePacket.class);

        packetTypeMap.put(SingleChatText_PACKET, SingleChatText_PACKET.class);
//        packetTypeMap.put(SingleChatText_RESPONSE, LoginResponsePacket.class);

        packetTypeMap.put(SendSingleChatFile_REQUEST, SendSingleChatFileRequestPacket.class);
        packetTypeMap.put(SendSingleChatFile_RESPONSE, SendSingleChatFileResponsePacket.class);

        packetTypeMap.put(SingleChatFile_PACKET, SingleChatFile_PACKET.class);
//        packetTypeMap.put(SingleChatFile_RESPONSE, LoginResponsePacket.class);

        packetTypeMap.put(ReadSingleChatMsg_REQUEST, ReadSingleChatMsgRequestPacket.class);
//        packetTypeMap.put(ReadSingleChatMsg_RESPONSE,Reads.class);

        packetTypeMap.put(GetSingleChatMsgNotRead_REQUEST, GetSingleChatMsgNotReadRequestPacket.class);
        packetTypeMap.put(GetSingleChatMsgNotRead_RESPONSE, GetSingleChatMsgNotReadResponsePacket.class);

        packetTypeMap.put(GetSingleChatTextNotRead_PACKET, GetSingleChatTextNotRead_PACKET.class);

        packetTypeMap.put(GetGroupChatMsgNotRead_REQUEST, GetGroupChatMsgNotReadRequestPacket.class);
        packetTypeMap.put(GetGroupChatMsgNotRead_RESPONSE, GetGroupChatMsgNotReadResponsePacket.class);

        packetTypeMap.put(GetGroupChatTextNotRead_PACKET, GetGroupChatTextNotRead_PACKET.class);

        packetTypeMap.put(GetFriendInfoByID_REQUEST, GetFriendInfoByIDRequestPacket.class);
        packetTypeMap.put(GetFriendInfoByID_RESPONSE, GetFriendInfoByIDResponsePacket.class);

        packetTypeMap.put(GetGroupsInfoOfUser_REQUEST, GetGroupsInfoOfUserRequestPacket.class);
        packetTypeMap.put(GetGroupsInfoOfUser_RESPONSE, GetGroupsInfoOfUserResponsePacket.class);

        packetTypeMap.put(GetAllFriendInfo_REQUEST, GetAllFriendInfoRequestPacket.class);
        packetTypeMap.put(GetAllFriendInfo_RESPONSE, GetAllFriendInfoResponsePacket.class);

        packetTypeMap.put(FreshAllFriendInfo_REQUEST, FreshAllFriendInfoRequestPacket.class);
        packetTypeMap.put(FreshAllFriendInfo_RESPONSE, FreshAllFriendInfoResponsePacket.class);

        packetTypeMap.put(GetFriendIDOnline_REQUEST, GetFriendIDOnlineRequestPacket.class);
        packetTypeMap.put(GetFriendIDOnline_RESPONSE, GetFriendIDOnlineResponsePacket.class);

        packetTypeMap.put(DeleteFriend_REQUEST, DeleteFriendRequestPacket.class);
        packetTypeMap.put(DeleteFriend_RESPONSE, DeleteFriendResponsePacket.class);

        packetTypeMap.put(FDeleteMe_Packet, FDeleteMe_Packet.class);
        packetTypeMap.put(ReciveFDeleteMe_REQUEST, ReciveFDeleteMeRequestPAcket.class);
        packetTypeMap.put(ReciveFDeleteMe_RESPONSE, ReciveFDeleteMeReponsePAcket.class);


        packetTypeMap.put(ExitGroup_REQUEST, ExitGroupRequestPacket.class);
        packetTypeMap.put(ExitGroup_RESPONSE, ExitGroupResponsePacket.class);

        packetTypeMap.put(SendExitGroupToAdmi_PACKET, SendExitGroupToAdmi_PACKET.class);
        packetTypeMap.put(GroupAdmiReciveExitGroup_REQUEST, GroupAdmiReciveExitGroupRequestPacket.class);

        packetTypeMap.put(GroupAdmiReciveExitGroup_RESPONSE, GroupAdmiReciveExitGroupResponsePacket.class);

        packetTypeMap.put(UpRequestMsgState_REQUEST, UpRequestMsgStateRequestPacket.class);
        packetTypeMap.put(UpRequestMsgState_RESPONSE, UpRequestMsgStateResponsePacket.class);

        packetTypeMap.put(AddFriend_REQUEST, AddFriendRequestPacket.class);
        packetTypeMap.put(AddFriend_RESPONSE, AddFriendResponsePacket.class);
        packetTypeMap.put(AddMeAsFriend_PACKET, AddMeAsFriend_PACKET.class);

        packetTypeMap.put(GetUserIcOfAddMeAsFriend_REQUEST, GetUserIcOfAddMeAsFriendRequestPacket.class);
        packetTypeMap.put(GetUserIcOfAddMeAsFriend_RESPONSE, GetUserIcOfAddMeAsFriendResponsePacket.class);

        packetTypeMap.put(GetUIcOfAgreeYourFriend_Request, GetUIcOfAgreeYourFriendRequestPacket.class);
        packetTypeMap.put(GetUIcOfAgreeYourFriend_RESPONSE, GetUIcOfAgreeYourFriendResponsePacket.class);


        packetTypeMap.put(AddMeFriendReturn_REQUEST, AddMeFriendReturnRequestPacket.class);
        packetTypeMap.put(AddMeFriendReturn_RESPONSE, AddMeFriendReturnResponsePacket.class);

        packetTypeMap.put(AgreeYourFriendRequest_PACKET, AgreeYourFriendRequest_PACKET.class);
        packetTypeMap.put(DisAgreeYourFriendRequest_PACKET, DisAgreeYourFriendRequest_PACKET.class);

        packetTypeMap.put(AddFriendResult_REQUEST, AddFriendResultRequestPacket.class);
        packetTypeMap.put(AddFriendResult_RESPONSE, AddFriendResultResponsePacket.class);

        packetTypeMap.put(AddGroup_REQUEST, AddGroupRequestPacket.class);
        packetTypeMap.put(AddGroup_RESPONSE, AddGroupResponsePacket.class);
        packetTypeMap.put(SendAdmiAddGroup_Packet, SendAdmiAddGroup_Packet.class);

        packetTypeMap.put(AddGroupReturn_REQUEST, AddGroupReturnRequestPacket.class);
        packetTypeMap.put(AddGroupReturn_RESPONSE, AddGroupReturnResponsePacket.class);

        packetTypeMap.put(SendUserAddGroupResult_PACKET, SendUserAddGroupResult_PACKET.class);
        packetTypeMap.put(SendAdmiAddGroupHandlered_PACKET, SendAdmiAddGroupHandlered_PACKET.class);

        packetTypeMap.put(ReadAddGroupResult_REQUEST, ReadaddGroupResultRequestPacket.class);
        packetTypeMap.put(ReadAddGroupResult_RESPONSE, ReadaddGroupResultResponsePacket.class);

        packetTypeMap.put(GetRequestFriendOrGroup_REQUEST, GetRequestFriendOrGroupRequestPacket.class);
        packetTypeMap.put(GetRequestFriendOrGroup_RESPONSE, GetRequestFriendOrGroupResponsePacket.class);

        packetTypeMap.put(GetPersonalInfo_REQUEST, GetPersonalInfoRequestPacket.class);
        packetTypeMap.put(GetPersonalInfo_RESPONSE, GetPersonalInfoResponsePacket.class);

        packetTypeMap.put(SearchAddUser_REQUEST, SearchAddUserRequestPacket.class);
        packetTypeMap.put(SearchAddUser_RESPONSE, SearchAddUserResponsePacket.class);

        packetTypeMap.put(SearchAddGroup_REQUEST, SearchAddGroupRequestPacket.class);
        packetTypeMap.put(SearchAddGroup_RESPONSE, SearchAddGroupResponsePacket.class);

        packetTypeMap.put(UpPersonalIcon_REQUEST, UpPersonalIconRequestPacket.class);
        packetTypeMap.put(UpPersonalIcon_RESPONSE, UpPersonalIconResponsePacket.class);

        packetTypeMap.put(GetPerIcon_REQUEST, GetPerIconRequestPacket.class);
        packetTypeMap.put(GetPerIcon_RESPONSE, GetPerIconResponsePacket.class);

        packetTypeMap.put(UpPeronalInfo_REQUEST, UpPeronalInfoRequestPacket.class);
        packetTypeMap.put(UpPeronalInfo_RESPONSE, UpPeronalInfoReponsePacket.class);

        packetTypeMap.put(UpFriendRemark_REQUEST, UpFriendRemarkRequestPacket.class);
        packetTypeMap.put(UpFriendRemark_RESPONSE, UpFriendRemarkResponsePacket.class);

        packetTypeMap.put(AddUserToFriendList_REQUEST, AddUserToFriendListRequestPacket.class);
        packetTypeMap.put(AddUserToFriendList_RESPONSE, AddUserToFriendListResponsePacket.class);

        packetTypeMap.put(UpGroupRemark_REQUEST, UpGroupRemarkRequestPacket.class);
        packetTypeMap.put(UpGroupRemarkt_RESPONSE, UpGroupRemarkResponsePacket.class);

        packetTypeMap.put(UpGroupPart_REQUEST, UpGroupPartRequestPacket.class);
        packetTypeMap.put(UpGroupPart_RESPONSE, UpGroupPartResponsePacket.class);

        packetTypeMap.put(GetGroupAllUser_REQUEST, GetGroupAllUserRequestPacket.class);
        packetTypeMap.put(GetGroupAllUser_RESPONSE, GetGroupAllUserResponsePacket.class);

        packetTypeMap.put(GetGroupUserIconByPh_REQUEST, GetGroupUserIconByPhRequestPacket.class);
        packetTypeMap.put(GetGroupUserIconByPh_RESPONSE, GetGroupUserIconByPhResponsePacket.class);

        packetTypeMap.put(CreateCorpration_REQUEST, CreateCorprationRequestPacket.class);
        packetTypeMap.put(CreateCorpration_RESPONSE, CreateCorprationResponsePacket.class);

        packetTypeMap.put(CreateGroup_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CreateGroup_RESPONSE, CreateGroupResponsePacket.class);

        packetTypeMap.put(AddGroupToGroupList_REQUEST, AddGroupToGroupListRequestPacket.class);
        packetTypeMap.put(AddGroupToGroupList_RESPONSE, AddGroupToGroupListResponsePacket.class);

        packetTypeMap.put(GetCourseSchOfUID_REQUEST, GetCourseSchByUIDRequestPacket.class);
        packetTypeMap.put(GetCourseSchOfUID_RESPONSE, GetCourseSchByUIDResponsePacket.class);

        packetTypeMap.put(GetScoreOfUID_REQUEST, GetScoreOfUIDRequestPacket.class);
        packetTypeMap.put(GetScoreOfUID_RESPONSE, GetScoreOfUIDResponsePacket.class);


        packetTypeMap.put(LoadCourseOfCorp_REQUEST, LoadCourseOfCorpRequestPacket.class);
        packetTypeMap.put(LoadCourseOfCorp_RESPONSE, LoadCourseOfCorpResponsePacket.class);

        packetTypeMap.put(SchduleArrangement_REQUEST, SchduleArrangementRequestPacket.class);
        packetTypeMap.put(SchduleArrangement_RESPONSE, SchduleArrangementResponsePacket.class);

        packetTypeMap.put(SendAdmiDutySche_PACKET, SendAdmiDutySche_PACKET.class);
        packetTypeMap.put(SendAllUsersDutyNotiNO_PACKET, SendAllUsersDutyNotiNO_PACKET.class);

        packetTypeMap.put(GetSDOfGid_REQUEST, GetSDOfGidRequestPacket.class);
        packetTypeMap.put(GetSDOfGid_RESPONSE, GetSDOfGidResponsePacket.class);

        packetTypeMap.put(GetAllSD_REQUEST, GetAllSDRequestPacket.class);
        packetTypeMap.put(GetAllSD_RESPONSE, GetAllSDResponsePacket.class);

        packetTypeMap.put(GetAllDutyNotiNotRead_REQUEST, GetAllDutyNotiNotReadRequestPacket.class);
        packetTypeMap.put(GetAllDutyNotiNotRead_RESPONSE, GetAllDutyNotiNotReadResponsePacket.class);

        packetTypeMap.put(ReadDutyNoti_REQUEST, ReadDutyNotiRequestPacket.class);
        packetTypeMap.put(ReadDutyNoti_RESPONSE, ReadDutyNotiResponsePacket.class);

        packetTypeMap.put(SearchEmptyCourse_REQUEST, SearchEmptyCourseRequestPacket.class);
        packetTypeMap.put(SearchEmptyCourse_RESPONSE, SearchEmptyCourseResponsePacket.class);

        packetTypeMap.put(CorpLoadCourseRs_REQUEST, CorpLoadCourseRsRequestPacket.class);
        packetTypeMap.put(CorpLoadCourseRs_RESPONSE, CorpLoadCourseRsResponsePacket.class);

        packetTypeMap.put(AlterCorpYearTerm_REQUEST, AlterCorpYearTermRequestPacket.class);
        packetTypeMap.put(AlterCorpYearTerm_RESPONSE, AlterCorpYearTermResponsePacket.class);

        packetTypeMap.put(AddCorApPart_REQUEST, AddCorApPartRequestPacket.class);
        packetTypeMap.put(AddCorApPart_RESPONSE, AddCorApPartResponsePacket.class);

        packetTypeMap.put(DeleteCorpPart_REQUEST, DeleteCorpPartRequestPacket.class);
        packetTypeMap.put(DeleteCorpPart_RESPONSE, DeleteCorpPartResponsePacket.class);

        packetTypeMap.put(SendUserDeleteCorpPart_PACKET, SendUserDeleteCorpPart_PACKET.class);

        packetTypeMap.put(AlterCorpPart_REQUEST, AlterCorpPartRequestPacket.class);

        packetTypeMap.put(AlterCorpPart_RESPONSE, AlterCorpPartResponsePacket.class);
        packetTypeMap.put(SendUserAlterCorpPart_PACKET, SendUserAlterCorpPart_PACKET.class);

        packetTypeMap.put(AlterCorpPos_REQUEST, AlterCorpPosRequestPacket.class);
        packetTypeMap.put(AlterCorpPos_RESPONSE, AlterCorpPosResponsePacket.class);

        packetTypeMap.put(GetUDtByDTID_REQUEST, GetUDtByDTIDRequestPacket.class);
        packetTypeMap.put(GetUDtByDTID_RESPONSE, GetUDtByDTIDResponsePacket.class);


        packetTypeMap.put(GetUDtICByDTICID_REQUEST, GetUDtICByDTICIDRequestPacket.class);
        packetTypeMap.put(GetUDtICByDTICID_RESPONSE, GetUDtICByDTICIDResponsePacket.class);

        packetTypeMap.put(GetUNDtIDs_REQUEST, GetUNDtIDsRequestPacket.class);
        packetTypeMap.put(GetUNDtIDs_RESPONSE, GetUNDtIDsResponsePacket.class);

        packetTypeMap.put(GetUsODtIDs_REQUEST, GetUsODtIDsRequestPacket.class);
        packetTypeMap.put(GetUsODtIDs_RESPONSE, GetUsODtIDsResponsePacket.class);

        packetTypeMap.put(GetDongtaiByDTID_REQUEST, GetDongtaiByDTIDRequestPacket.class);
        packetTypeMap.put(GetDongtaiByDTID_RESPONSE, GetDongtaiByDTIDResponsePacket.class);

        packetTypeMap.put(GetDongtaiUICByDTID_REQUEST, GetDongtaiUICByDTID_RequestPacket.class);
        packetTypeMap.put(GetDongtaiUICByDTID_RESPONSE, GetDongtaiUICByDTIDResponsePacket.class);

        packetTypeMap.put(GetDongtaiICByDTID_REQUEST, GetDongtaiICByDTIDRequestPacket.class);
        packetTypeMap.put(GetDongtaiICByDTID_RESPONSE, GetDongtaiICByDTID_ResponsePacket.class);

        packetTypeMap.put(GetNewDongtaiIDs_REQUEST, GetNewDongtaiIDsRequestPacket.class);
        packetTypeMap.put(GetNewDongtaiIDs_RESPONSE, GetNewDongtaiIDsResponsePacket.class);

        packetTypeMap.put(GetOldDongtaiIDs_REQUEST, GetOldDongtaiIDsRequestPacket.class);
        packetTypeMap.put(GetOldDongtaiIDs_RESPONSE, GetOldDongtaiIDsResponsePacket.class);

        packetTypeMap.put(AddDongtai_REQUEST, AddDongtaiRequestPacket.class);
        packetTypeMap.put(AddDongtai_RESPONSE, AddDongtaiResponsePacket.class);

        packetTypeMap.put(AddDongTaiImage_REQUEST, AddDongTaiImageRequestPacket.class);
        packetTypeMap.put(AddDongTaiImage_RESPONSE, AddDongTaiImageResponsePacket.class);

        packetTypeMap.put(GetIndexInfoOfPh_REQUEST, GetIndexInfoOfPhRequestPacket.class);
        packetTypeMap.put(GetIndexInfoOfPh_RESPONSE, GetIndexInfoOfPhResponsePacket.class);

        packetTypeMap.put(GetIndexICOfPh_REQUEST, GetIndexICOfPhRequestPacket.class);
        packetTypeMap.put(GetIndexICOfPh_RESPONSE, GetIndexICOfPhResponsePacket.class);

        //社团组织任命职位
        packetTypeMap.put(AppointCorpPos_REQUEST, AppointCorpPosRequestPacket.class);
        packetTypeMap.put(AppointCorpPos_RESPONSE, AppointCorpPosResponsePacket.class);

        //添加好友时    首先查询     获得对方的头像
        packetTypeMap.put(GetUICOfSearchAddUser_Request, GetUICOfSearchAddUserRequestPacket.class);
        packetTypeMap.put(GetUICOfSearchAddUser_RESPONSE, GetUICOfSearchAddUserResponsePacket.class);

        //更改群头像
        packetTypeMap.put(UpGroupIcon_Request, UpGroupIconRequestPacket.class);
        packetTypeMap.put(UpGroupIcon_RESPONSE, UpGroupIconResponsePacket.class);

        //获得某群头像
        packetTypeMap.put(GetGroupIcByGid_Request, GetGroupIcByGidRequestPacket.class);
        packetTypeMap.put(GetGroupIcByGid_RESPONSE, GetGroupIcByGidResponsePacket.class);

        //获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        packetTypeMap.put(GetNotiIconOfGroup_Request, GetNotiIconOfGroupRequestPacket.class);
        packetTypeMap.put(GetNotiIconOfGroup_RESPONSE, GetNotiIconOfGroupResponsePacket.class);

        //获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        packetTypeMap.put(GetNotiIconOfUser_Request, GetNotiIconOfUserRequestPacket.class);
        packetTypeMap.put(GetNotiIconOfUser_RESPONSE, GetNotiIconOfUserResponsePacket.class);

        //进入好友列表   如果本地没有好友的头像   获取好友的的头像
        packetTypeMap.put(GetFriendListUIcByPh_Request, GetFriendListUIcByPhRequestPacket.class);
        packetTypeMap.put(GetFriendListUIcByPh_RESPONSE, GetFriendListUIcByPhResponsePacket.class);

        //添加群时    首先查询     获得群的头像
        packetTypeMap.put(GetGroupICOfSearchAddGroup_Request, GetGroupICOfSearchAddGroupRequestPacket.class);
        packetTypeMap.put(GetGroupICOfSearchAddGroup_RESPONSE, GetGroupICOfSearchAddGroupResponsePacket.class);

        //添加群  群管理员要获得对方的头像
        packetTypeMap.put(GetUserIcOfAddGroup_REQUEST, GetUserIcOfAddGroupRequestPacket.class);
        packetTypeMap.put(GetUserIcOfAddGroup_RESPONSE, GetUserIcOfAddGroupResponsePacket.class);

        //获得某用户头像
        packetTypeMap.put(GetUserIcOfPh_REQUEST, GetUserIcOfPhRequestPacket.class);
        packetTypeMap.put(GetUserIcOfPh_RESPONSE, GetUserIcOfPhResponsePacket.class);

        //获得用户的某群的信息   一般用在更新某群信息时
        packetTypeMap.put(GetGroupInfoByGid_REQUEST, GetGroupInfoByGidRequestPacket.class);
        packetTypeMap.put(GetGroupInfoByGid_RESPONSE, GetGroupInfoByGidResponsePacket.class);

        //自己登陆   有人异地登陆  顶替自己
        packetTypeMap.put(loginByOther_Packet, LoginByOther_PACKET.class);

        // 查询空教室
        packetTypeMap.put(SearchEmptyClassroom_REQUEST, SearchEmptyClassroomRequestPacket.class);
        packetTypeMap.put(SearchEmptyClassroom_RESPONSE, SearchEmptyClassroomResponsePacket.class);


        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {

        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
//        String a = new String(bytes,0,bytes.length);
//        System.out.println("encode  "+a);
        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeInt(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        int command = byteBuf.readInt();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
//        String a = new String(bytes,0,bytes.length);
//        System.out.println("decode  "+a);
        
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {

           return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(int command) {

        return packetTypeMap.get(command);
    }
}
