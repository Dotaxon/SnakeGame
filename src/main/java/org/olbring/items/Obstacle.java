package org.olbring.items;

import org.olbring.tools.Tools;

public class Obstacle extends Item{

    public Obstacle (String obstaclePath){
        icon = Tools.loadIcon(obstaclePath);
    }

}
