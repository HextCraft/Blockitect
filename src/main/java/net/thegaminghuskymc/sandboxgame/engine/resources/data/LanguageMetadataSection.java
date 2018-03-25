package net.thegaminghuskymc.sandboxgame.engine.resources.data;

import net.thegaminghuskymc.sandboxgame.engine.modding.Side;
import net.thegaminghuskymc.sandboxgame.engine.modding.SideOnly;
import net.thegaminghuskymc.sandboxgame.engine.resources.Language;

import java.util.Collection;

@SideOnly(Side.CLIENT)
public class LanguageMetadataSection implements IMetadataSection
{
    private final Collection<Language> languages;

    public LanguageMetadataSection(Collection<Language> languagesIn)
    {
        this.languages = languagesIn;
    }

    public Collection<Language> getLanguages()
    {
        return this.languages;
    }
}