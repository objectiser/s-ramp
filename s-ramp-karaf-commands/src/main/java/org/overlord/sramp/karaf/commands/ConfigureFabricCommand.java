/*
 * Copyright 2014 JBoss Inc
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
package org.overlord.sramp.karaf.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.felix.gogo.commands.Command;
import org.overlord.commons.codec.AesEncrypter;
import org.overlord.commons.karaf.commands.configure.AbstractConfigureFabricCommand;

/**
 * Karaf console command for use within JBoss Fuse. It should be used before the
 * fabric is created. It configures s-ramp fabric profiles, including overlord
 * profile and sramp profile.
 *
 * @author David Virgil Naranjo
 */
@Command(scope = "overlord:fabric:s-ramp", name = "configure")
public class ConfigureFabricCommand extends AbstractConfigureFabricCommand {

    private static String SRAMP_PROFILE_PATH;


    static {
        if (File.separator.equals("/")) { //$NON-NLS-1$
            SRAMP_PROFILE_PATH = "overlord/sramp.profile"; //$NON-NLS-1$
        } else {
            SRAMP_PROFILE_PATH = "overlord\\sramp.profile"; //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.karaf.shell.console.AbstractAction#doExecute()
     */
    @Override
    protected Object doExecute() throws Exception {
        super.doExecute();
        addHeaderProperties();
        updateSrampProperties();
        return null;
    }

    /**
     * Gets the fabric sramp profile path.
     *
     * @return the fuse config path
     */
    public String getSrampFabricProfilePath() {
        StringBuilder fuse_config_path = new StringBuilder();
        fuse_config_path.append(getFabricProfilesPath()).append(SRAMP_PROFILE_PATH).append(File.separator);
        return fuse_config_path.toString();
    }

    /**
     * Update sramp properties.
     *
     * @throws Exception
     *             the exception
     */
    protected void updateSrampProperties() throws Exception {
        String filePath = getSrampPropertiesFilePath();
        File srampFile = new File(filePath);
        if (srampFile.exists()) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(filePath);
                Properties props = new Properties();
                props.load(in);
                FileOutputStream out = null;
                String aesEncryptedValue = AesEncrypter.encrypt(password);
                StringBuilder aesEncrypterBuilder = new StringBuilder();
                aesEncrypterBuilder.append("$\\{crypt:").append(aesEncryptedValue).append("\\}"); //$NON-NLS-1$ //$NON-NLS-2$
                aesEncryptedValue = aesEncrypterBuilder.toString();
                try {
                    out = new FileOutputStream(filePath);
                    props.setProperty(ConfigureConstants.SRAMP_EVENTS_JMS_PASSWORD, aesEncryptedValue);
                    props.setProperty(ConfigureConstants.SRAMP_EVENTS_JMS_USER, ConfigureConstants.SRAMP_EVENTS_JMS_DEFAULT_USER);
                    props.store(out, null);
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        }
    }

    /**
     * Gets the sramp properties file path.
     *
     * @return the sramp properties file path
     */
    private String getSrampPropertiesFilePath() {
        StringBuilder fuse_config_path = new StringBuilder();
        fuse_config_path.append(getSrampFabricProfilePath()).append(ConfigureConstants.SRAMP_PROPERTIES_FILE_NAME);
        return fuse_config_path.toString();
    }

    /**
     * Adds the header properties.
     *
     * @throws Exception
     *             the exception
     */
    private void addHeaderProperties() throws Exception {

        String filePath = getOverlordPropertiesFilePath();

        Properties props = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(new File(filePath));
            props.load(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            props.setProperty(ConfigureConstants.SRAMP_HEADER_HREF, ConfigureConstants.SRAMP_HEADER_HREF_VALUE);
            props.setProperty(ConfigureConstants.SRAMP_HEADER_LABEL, ConfigureConstants.SRAMP_HEADER_LABEL_VALUE);
            props.setProperty(ConfigureConstants.SRAMP_HEADER_PRIMARY_BRAND, ConfigureConstants.SRAMP_HEADER_PRIMARY_BRAND_VALUE);
            props.setProperty(ConfigureConstants.SRAMP_HEADER_SECOND_BRAND, ConfigureConstants.SRAMP_HEADER_SECOND_BRAND_VALUE);
            props.store(out, null);

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
