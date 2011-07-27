/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.skhome.common.command;

import net.skhome.common.transfer.TransferObject;

/**
 * The main goal of this class is to decouple client, invoker and receiver in a chain of command. The invoker is the one which
 * invokes an action on the receiver, and the receiver is the one on which the command is executed.
 *
 * @author Sascha Krueger
 * @since 1.0
 */
public interface CommandObject {

	/**
	 * Returns the result of the command execution.
	 *
	 * @return result of command execution
	 */
	public TransferObject getResult();

	/**
	 * Sets the parameter that will be transfered to the receiver.
	 *
	 * @param parameter
	 * 		parameter that will be transfered to the receiver
	 */
	public void setParameter(TransferObject parameter);

	/**
	 * Executes the command at the receiver.
	 */
	public void execute();

}
