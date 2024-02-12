/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * @author fedya
 *
 */
public class ServerTechnicalInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1148766333139342893L;

    private Map<String, String> info;

    /**
     * @return the info
     */
    public Map<String, String> getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(Map<String, String> info) {
        this.info = info;
    }

}
