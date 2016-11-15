package cc.suitalk.arbitrarygen.template.psychic.base;

import net.sf.json.JSONObject;

/**
 * Created by albieliang on 16/11/14.
 */

public interface PsychicExecutor {

    String getName();

    JSONObject exec(JSONObject inputJSON);
}
