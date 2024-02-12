/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class ServerInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7056060003187438749L;
    public String address;
    public String locale;
    public String name;

    @Override
    public String toString() {
        return address + " " + locale + " " + name;

    }
}
