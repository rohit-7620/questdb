/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2024 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.griffin.engine.functions.constants;

import io.questdb.cairo.arr.ArrayTypeDriver;
import io.questdb.cairo.arr.ArrayView;
import io.questdb.cairo.arr.NoopArrayWriteState;
import io.questdb.cairo.sql.ArrayFunction;
import io.questdb.cairo.sql.Record;
import io.questdb.griffin.PlanSink;
import io.questdb.std.str.StringSink;

public final class NullArrayConstant extends ArrayFunction implements ConstantFunction {
    public NullArrayConstant(int columnType) {
        try {
            this.type = columnType;
        } catch (Throwable th) {
            close();
            throw th;
        }
    }

    @Override
    public ArrayView getArray(Record rec) {
        return ArrayConstant.NULL;
    }

    @Override
    public void toPlan(PlanSink sink) {
        StringSink strSink = new StringSink();
        ArrayTypeDriver.arrayToJson(ArrayConstant.NULL, strSink, NoopArrayWriteState.INSTANCE);
        sink.val("ARRAY" + strSink);
    }
}
