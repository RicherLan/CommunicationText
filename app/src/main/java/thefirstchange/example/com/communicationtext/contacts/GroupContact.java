package thefirstchange.example.com.communicationtext.contacts;

import java.io.Serializable;

import thefirstchange.example.com.communicationtext.gson.UserGroup;

public class GroupContact implements Serializable {
        private UserGroup userGroup;
        private String name;
        private int mType;

        public GroupContact(UserGroup userGroup, int type) {
            this.userGroup = userGroup;
            mType = type;
            name= userGroup.getGroupname();
        }
        public GroupContact(String name, int mType){
            this.name=name;
            this.mType=mType;
        }

        public UserGroup getUserGroup() {
            return userGroup;
        }
        public String getmName(){
            return name;
        }

        public int getmType() {
            return mType;
        }

    }
