package hr.as2.inf.common.validations.validators;

import hr.as2.inf.common.validations.validators.iban.AS2Iban;

/*
 * https://svn.apache.org/repos/asf/bval/trunk/bval-extras/src/main/java/org/apache/bval/extras/constraints/checkdigit/IBANValidator.java
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * <b>IBAN</b> (International Bank Account Number) Check Digit
 * calculation/validation.
 * <p>
 * This rountine is based on the ISO 7064 Mod 97,10 check digit caluclation
 * routine.
 * <p>
 * The two check digit characters in a IBAN number are the third and fourth
 * characters in the code. For <i>check digit</i> calculation/validation the
 * first four characters are moved to the end of the code. So
 * <code>CCDDnnnnnnn</code> becomes <code>nnnnnnnCCDD</code> (where
 * <code>CC</code> is the country code and <code>DD</code> is the check digit).
 * For check digit calcualtion the check digit value should be set to zero (i.e.
 * <code>CC00nnnnnnn</code> in this example.
 * <p>
 * For further information see <a
 * href="http://en.wikipedia.org/wiki/International_Bank_Account_Number"
 * >Wikipedia - IBAN number</a>.
 */
public class AS2IBANValidator extends AS2Validator {
	@Override
	protected boolean isValid(Object value) {
		return AS2Iban.isCheckDigitValid(value.toString());
	}
}
