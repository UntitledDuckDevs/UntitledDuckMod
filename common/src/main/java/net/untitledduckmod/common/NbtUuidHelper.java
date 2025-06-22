package net.untitledduckmod.common;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.util.Uuids;
import java.util.UUID;

public class NbtUuidHelper {
    public static boolean containsUuid(NbtCompound tag, String key) {
        NbtElement nbtElement = tag.get(key);
        return nbtElement != null && nbtElement.getNbtType() == NbtIntArray.TYPE && ((NbtIntArray)nbtElement).getIntArray().length == 4;
    }

    private static UUID toUuid(NbtElement element) {
        if (element == null) {
            throw new NullPointerException("NbtElement is null");
        }

        if (element.getNbtType() != NbtIntArray.TYPE) {
            String var10002 = NbtIntArray.TYPE.getCrashReportName();
            throw new IllegalArgumentException("Expected UUID-Tag to be of type " + var10002 + ", but found " + element.getNbtType().getCrashReportName() + ".");
        } else {
            int[] is = ((NbtIntArray)element).getIntArray();
            if (is.length != 4) {
                throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + is.length + ".");
            } else {
                return Uuids.toUuid(is);
            }
        }
    }

    public static UUID getUuid(NbtCompound tag, String key) {
        return toUuid(tag.get(key));
    }
}
