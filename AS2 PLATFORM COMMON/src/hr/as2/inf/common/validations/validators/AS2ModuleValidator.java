package hr.as2.inf.common.validations.validators;

import hr.as2.inf.common.types.AS2String;

public final class AS2ModuleValidator {

	private static final String _STR_EMPTY = ""; 

	public static int rac_mod10(String partija)	{
		char znamenka;
		int itmp;
		int suma = 0;

		for (int iterator = 1; iterator <= partija.length(); iterator++) {
			znamenka = partija.charAt(iterator - 1);
			itmp = Integer.parseInt(_STR_EMPTY + znamenka);
			suma += itmp * (((iterator + 1) % 2) + 1);
		}
		return (suma % 10);
	}

	public static int rac_mod10_11(String partija) {
		char znamenka;
		int suma = 0;
		int itmp;

		for (int iterator = 1; iterator <= partija.length(); iterator++) {
			znamenka = partija.charAt(iterator - 1);
			itmp = Integer.parseInt(_STR_EMPTY + znamenka);
			suma += itmp * ((partija.length() - iterator) % 6 + 2);
		}

		itmp = 11 - suma % 11;
		if (itmp == 10 || itmp == 11) {
			itmp = 0;
		}
		return itmp;
	}

	public static int rac_modiso(String partija) {
		char znamenka;
		int m = 10;
		int p = 10;

		for (int iterator = 1; iterator <= partija.length(); iterator++) {
			znamenka = partija.charAt(iterator - 1);
			p += Integer.parseInt(_STR_EMPTY + znamenka);
			p %= m;
			p = p == 0 ? m : p;
			p *= 2;
			p %= (m + 1);
		}
		return (11 - p) % m;
	}

	public static int fnKey11_a(String pSample) {
		int result = 0;
		int length = pSample.length();
		int counter;
		for (counter = 0; counter < length; counter++)	{
			int index = length - counter - 1;
			result += Integer.parseInt(pSample.substring(index, index + 1)) * (2 + counter);
		}
		result = 11 - result % 11;
		if (result > 9){ // result == 10 || result == 11
			result = 0;
		}
		return result;
	}

	public static boolean isPartnoValid(String partno)	{
		boolean res = false;
		if (partno != null && partno.trim().length() == 10) {
			int kz = calcCheckNumber(partno.trim().substring(0, 9));
			res = (partno.substring(9, 10).equals(_STR_EMPTY + kz));
		}
		return res;
	}

	public static String getFullPartNo(String partno) {
		if (partno.trim().length() == 9) {
			int kz = calcCheckNumber(partno);
			partno = partno.concat(_STR_EMPTY + kz);
		}
		else if (partno.trim().length() != 10)	{
			partno = _STR_EMPTY;
		}
		return partno;
	}

	public static String getFullCientNo(String clientno) {
		return clientno.concat(_STR_EMPTY + fnKey11_a(clientno));
	}

	public static boolean isClientNoValid(String clientno)	{
		boolean res = true;
		if (clientno != null && !clientno.trim().equals("")) {
			clientno = clientno.trim();
			String tmp = clientno.substring(0, clientno.length() - 1);
			try	{
				int ctrNum = Integer.parseInt(clientno.substring(clientno.length() - 1));
				if (fnKey11_a(tmp) == ctrNum) {
					res = true;
				} else {
					res = false;
				}
			}
			catch (NumberFormatException e)	{
				res = false;
			}
		}
		return res;
	}

	public static boolean isVBDIValid(String vbdi) {
		if(!AS2String.isEmpty(vbdi) && !vbdi.equals("0")) {
			if (vbdi.trim().length() != 7) {
				return false;
			}
			int kz = rac_modiso(vbdi.trim().substring(0, 6));
			return (vbdi.substring(6, 7).equals(_STR_EMPTY + kz));
		}
		return true;
	}

	public static int calcCheckNumber(String partno) {
		return rac_modiso(partno);
	}
}