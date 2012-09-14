/*
 * Copyright 2012 JBoss Inc
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
package org.overlord.sramp.atom.archive;

import org.s_ramp.xmlns._2010.s_ramp.BaseArtifactType;

/**
 * Models a single entry in an S-RAMP archive.  Each entry in a valid
 * S-RAMP archive must include an artifact file (e.g. artifact.wsdl) and an
 * associated Atom Entry file (e.g. artifact.wsdl.atom).  These two files
 * make up a single entry in the archive (the content and the meta-data for
 * an S-RAMP artifact).
 *
 * Note that the Atom entry file is a full artifact Entry, so this class
 * automatically wraps and unwraps the {@link BaseArtifactType} from the
 * Atom entry.  In other words, this model represents a single archive
 * entry as the content and a {@link BaseArtifactType}.
 *
 * @author eric.wittmann@redhat.com
 */
public class SrampArchiveEntry {

	private String path;
	private BaseArtifactType artifact;

	/**
	 * Constructor.
	 */
	public SrampArchiveEntry() {
	}

	/**
	 * Constructor.
	 * @param path
	 * @param artifact
	 */
	public SrampArchiveEntry(String path, BaseArtifactType artifact) {
		setPath(path);
		setArtifact(artifact);
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the artifact
	 */
	public BaseArtifactType getArtifact() {
		return artifact;
	}

	/**
	 * @param artifact the artifact to set
	 */
	public void setArtifact(BaseArtifactType artifact) {
		this.artifact = artifact;
	}

}
