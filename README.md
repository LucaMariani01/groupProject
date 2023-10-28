# Group Project
Group Project exam for University of Camerino, Computer Science L-31. 
The group is composed by Luca Mariani, Diego Pennesi, Filippo Reucci.

# Ring PDB Analyzer
The focus of this group project is, given a list of PDBs contained in a JSON file, building a set of Arc Annotated Sequence (AAS) files in the given directory.

## RingPdbAnalyzer.jar usage examples

* `> java -jar RingPdbAnalyzer.jar -i RepeatsDB-table.json -o Users/name/Desktop`

Produces a list of AAS file contained in the Users/name/Desktop directory, corresponding to the PDBs contained in the RepeatsDB-table.json file.

## Accepted Input file formats 
* PDB JSON format, download link: <https://www.rcsb.org/>

## Result formats
Arc Annotated Sequence (AAS) file, inclueds sequence and bond list, expressed in the format `(i_1,j_1);(i_2,j_2); ... ;(i_m,j_m)` where each index 
`i_k, j_k` is withinthe range `[1,n]` where `n` is the length
of the primary sequence and `i_k < j_k`  for all `k`.
