// SPDX-License-Identifier: Apache-2.0

pragma solidity >=0.7.1;

import "./Initiate.sol";
import "./Utils.sol";
import "./Voting.sol";
import "./BLS.sol";

contract Counting{

    uint constant t = 3;
    Initiate iv = Initiate(/* Initiate Contract Address */);
    Voting vote = Voting(/* Voting Contract Address */);

    uint256 constant p = 21888242871839275222246405745257275088696311157297823662689037894645226208583;

    bytes [] ballots1;

    function getballots(uint round) public  view returns(Utils.record [] memory){
        Utils.voting memory v = iv.getVoting(round);
        Utils.record [] memory ballots;
        if(v.deadline<block.timestamp){
            return vote.getBallots(round);
        }
        return ballots;
    }


    struct tmp{
        Utils.record [] s_i;
        string ballot;
        bool isDecrypt;
        bool isNotEmpty;
    }

    struct k{
        mapping (uint => tmp) map1;
        uint [10] res;
        uint [] keys;
        uint count;
        bool isNotEmpty;
    }
    mapping (uint=> k) map;
     
    function uploadSi( uint round, uint index, Utils.record memory s_i) public {
        if(map[round].map1[index].isNotEmpty==false){
            map[round].map1[index].isNotEmpty==true;
            map[round].keys.push(index);
        }
        if(map[round].map1[index].isDecrypt)
            return;
        map[round].map1[index].s_i.push(s_i);
        map[round].isNotEmpty=true;
        if(map[round].map1[index].s_i.length==t){
            map[round].map1[index].ballot=decrypt(map[round].map1[index].s_i,vote.getBallots(round)[index]);
            map[round].map1[index].isDecrypt=true;
            map[round].count=map[round].count+1;
        }
    }

    function decrypt(Utils.record [] memory s_i, Utils.record memory ballot) public view returns(string memory){
        BLS.E1Point memory sum = BLS.E1Point(s_i[0].x,s_i[0].y);
        for(uint i=1;i<s_i.length;i++){
            BLS.E1Point memory tt = BLS.E1Point(s_i[i].x,s_i[i].y);
            sum = BLS.addCurveE1(sum,tt);
        }
        uint256 ballot1 = (ballot.x-sum.x)%p  + (ballot.y-sum.y)%p;
        return toString(ballot1);
    }

    function toBytes(uint256 x) public pure returns(bytes memory b){
        b = new bytes(32);
        assembly{mstore(add(b,32),x)}
    }

    function toString(uint256 x)public pure returns(string memory){
        bytes memory c = toBytes(x);
        return string(c);

    }

    function getChar(uint index, string memory s) public pure returns(bytes1){
        return bytes(s)[index];
    }


    function counting(uint round) public returns(uint [10] memory){
        Utils.record [] memory l = vote.getBallots(round);
        uint [10] memory u;
        if(map[round].count<l.length){
            return u;
        }
        uint [] memory keys = map[round].keys;
        for(uint i=0;i<keys.length;i++){
            tmp memory t1=map[round].map1[keys[i]];
            string memory ballot = t1.ballot;
            uint pp=0;
            uint qq=1;
            while(qq<bytes(ballot).length){
                if(getChar(pp, ballot)>=0x30&&getChar(pp, ballot)<=0x39){
                    map[round].res[uint8(getChar(p, ballot))-0x30]=map[round].res[uint8(getChar(p, ballot))-0x30]+1;
                }
                if(getChar(qq, ballot)==0x26){
                    pp++;
                    qq++;
                }
                else{
                    break;
                }
            }
            
        }
        return map[round].res;
    }

}