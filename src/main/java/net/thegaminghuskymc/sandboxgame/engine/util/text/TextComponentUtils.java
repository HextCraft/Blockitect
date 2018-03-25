package net.thegaminghuskymc.sandboxgame.engine.util.text;

import java.util.List;

public class TextComponentUtils
{
    public static ITextComponent processComponent(ITextComponent component)
    {
        ITextComponent itextcomponent;

        itextcomponent = component;

        if (component instanceof TextComponentScore) {
            TextComponentScore textcomponentscore = (TextComponentScore) component;
            String s = textcomponentscore.getName();
        }
        else if (component instanceof TextComponentString)
        {
            itextcomponent = new TextComponentString(((TextComponentString)component).getText());
        }
        else if (component instanceof TextComponentKeybind)
        {
            itextcomponent = new TextComponentKeybind(((TextComponentKeybind)component).getKeybind());
        }
        else
        {
            if (!(component instanceof TextComponentTranslation))
            {
                return component;
            }

            Object[] aobject = ((TextComponentTranslation)component).getFormatArgs();

            for (int i = 0; i < aobject.length; ++i)
            {
                Object object = aobject[i];

                if (object instanceof ITextComponent)
                {
                    aobject[i] = processComponent((ITextComponent)object);
                }
            }

            itextcomponent = new TextComponentTranslation(((TextComponentTranslation)component).getKey(), aobject);
        }

        Style style = component.getStyle();

        for (ITextComponent itextcomponent1 : component.getSiblings())
        {
            itextcomponent.appendSibling(processComponent(itextcomponent1));
        }

        return itextcomponent;
    }
}