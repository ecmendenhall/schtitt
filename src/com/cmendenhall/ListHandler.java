package com.cmendenhall;

import java.util.HashMap;

public class ListHandler implements PageHandler {

    public WebResource render(HashMap<String, String> params) {

        KeyValueStore.load();
        params.put("field1",   KeyValueStore.get("field1"));
        params.put("field2",   KeyValueStore.get("field2"));
        params.put("field3",   KeyValueStore.get("field3"));
        params.put("data", KeyValueStore.get("data"));

        return Template.render("list.html", params);
    }

}
