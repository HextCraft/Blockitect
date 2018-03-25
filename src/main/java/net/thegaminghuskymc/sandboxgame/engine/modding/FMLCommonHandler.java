/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.thegaminghuskymc.sandboxgame.engine.modding;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;

/**
 * The main class for non-obfuscated hook handling code
 *
 * Anything that doesn't require obfuscated or client/server specific code should
 * go in this handler
 *
 * It also contains a reference to the sided handler instance that is valid
 * allowing for common code to access specific properties from the obfuscated world
 * without a direct dependency
 *
 * @author cpw
 *
 */
public class FMLCommonHandler
{
    /**
     * The singleton
     */
    private static final FMLCommonHandler INSTANCE = new FMLCommonHandler();

    private FMLCommonHandler()
    {

    }

    public static FMLCommonHandler instance() {
        return INSTANCE;
    }

    @Nullable
    public InputStream loadLanguage(Map<String, String> table, InputStream inputstream) throws IOException
    {
        byte[] data = IOUtils.toByteArray(inputstream);

        boolean isEnhanced = false;
        for (String line : IOUtils.readLines(new ByteArrayInputStream(data), StandardCharsets.UTF_8))
        {
            if (!line.isEmpty() && line.charAt(0) == '#')
            {
                line = line.substring(1).trim();
                if (line.equals("PARSE_ESCAPES"))
                {
                    isEnhanced = true;
                    break;
                }
            }
        }

        if (!isEnhanced)
            return new ByteArrayInputStream(data);

        Properties props = new Properties();
        props.load(new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8));
        for (Entry<Object, Object> e : props.entrySet())
        {
            table.put((String)e.getKey(), (String)e.getValue());
        }
        props.clear();
        return null;
    }
}