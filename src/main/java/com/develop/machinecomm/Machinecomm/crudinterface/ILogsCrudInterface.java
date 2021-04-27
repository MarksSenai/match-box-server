package com.develop.machinecomm.Machinecomm.crudinterface;

import com.develop.machinecomm.Machinecomm.model.Logs;

public interface ILogsCrudInterface {
   public Logs findByUserAndMachineAllIgnoreCase(int user, int machine);
}