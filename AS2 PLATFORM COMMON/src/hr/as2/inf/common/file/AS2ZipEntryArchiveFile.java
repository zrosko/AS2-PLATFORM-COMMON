package hr.as2.inf.common.file;
/*
 * Copyright 2006-2007 Luca Garulli (luca.garulli--at--assetdata.it)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AS2ZipEntryArchiveFile implements AS2File{

	protected ZipEntry	file;
	protected ZipFile		zipFile;

	public AS2ZipEntryArchiveFile(ZipEntry entry, ZipFile iZipFile) {
		file = entry;
		zipFile = iZipFile;
	}

	public boolean exists() {
		return true;
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

	public AS2File[] listFiles() {
		Enumeration<? extends ZipEntry> e = zipFile.entries();
		ZipEntry entry;
		Set<AS2File> files = new HashSet<AS2File>();
		String entryName;
		String names[];
		while (e.hasMoreElements()) {
			entry = e.nextElement();
			entryName = entry.getName();

			if (entryName.length() > file.getName().length() && entryName.startsWith(file.getName())) {
				entryName = AS2FileUtility.removeLastSeparatorIfAny(entryName);
				String fileName = AS2FileUtility.removeLastSeparatorIfAny(file.getName());

				names = AS2FileUtility.getResourceNamesLastSeparator(entryName, AS2FileUtility.PATH_SEPARATOR_STRING, null);
				if (names[0] == null || names[0].equals(fileName))
					files.add(new AS2ZipEntryArchiveFile(entry, zipFile));
			}
		}

		AS2File[] vFile = new AS2File[files.size()];
		files.toArray(vFile);
		return vFile;
	}

	public String getName() {
		String entryName = AS2FileUtility.removeLastSeparatorIfAny(file.getName());

		String[] names = AS2FileUtility.getResourceNamesLastSeparator(entryName, AS2FileUtility.PATH_SEPARATOR_STRING, "");
		return names[1];
	}

	public InputStream getInputStream() {
		try {
			return zipFile.getInputStream(file);
		} catch (IOException e) {
			return null;
		}
	}

	public OutputStream getOutputStream() {
		throw new UnsupportedOperationException("Method not Implemented");
	}

	public long length() {
		return file.getSize();
	}

	@Override
	public String toString() {
		return file.getName();
	}

	public String getAbsolutePath() {
		return zipFile.getName() + "!" + AS2FileUtility.PATH_SEPARATOR_STRING + file.getName();
	}
}
