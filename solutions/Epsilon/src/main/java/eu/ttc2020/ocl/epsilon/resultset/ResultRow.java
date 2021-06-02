/**************************************************************************
Copyright 2019 Vietnamese-German-University

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author: ngpbh
***************************************************************************/

package eu.ttc2020.ocl.epsilon.resultset;

import java.util.HashMap;

public class ResultRow {
    private HashMap<String, String> cols;

    public HashMap<String, String> getCols() {
        return cols;
    }

    public void setCols(HashMap<String, String> cols) {
        this.cols = cols;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cols == null) ? 0 : cols.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResultRow other = (ResultRow) obj;
        if (cols == null) {
            if (other.cols != null)
                return false;
        } else if (!compareCols(other.cols))
            return false;
        return true;
    }

    private boolean compareCols(HashMap<String, String> obj) {
        if(cols.size() != obj.size())
            return false;
        return cols.equals(obj);
    }

    @Override
    public String toString() {
        return "\n\t[" + cols + "]";
    }
    
}
