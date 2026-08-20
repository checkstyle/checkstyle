module com.example.app { //indent:0 exp:0
        requires java.base; //indent:8 exp:4 warn
    /* c */   requires java.sql; //indent:4 exp:4
  exports com.example.api; //indent:2 exp:4 warn
    opens com.example.api to //indent:4 exp:4
      java.base; //indent:6 exp:8 warn
      uses com.example.api.Service; //indent:6 exp:4 warn
      provides com.example.api.Service with //indent:6 exp:4 warn
        com.example.app.ServiceImpl; //indent:8 exp:10 warn
} //indent:0 exp:0
