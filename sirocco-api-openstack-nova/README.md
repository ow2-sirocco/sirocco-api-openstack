# Notes on Nova API and its official documentation issues...

## Security Groups

### Adding rule to an existing group (secgroup-add-rule)

Here is the traces from the official nova client, looks like it means that we just want to add rule with CIDR...

    nova help secgroup-add-rule

    usage: nova secgroup-add-rule <secgroup> <ip-proto> <from-port> <to-port>
                                  <cidr>

    Add a rule to a security group.

    Positional arguments:
      <secgroup>   ID or name of security group.
      <ip-proto>   IP protocol (icmp, tcp, udp).
      <from-port>  Port at start of range.
      <to-port>    Port at end of range.
      <cidr>       CIDR for address range.

So...

    nova --debug secgroup-add-rule demo udp 33 33 10.0.0.0/16
    ...
    REQ: curl -i http://host:8774/v2/YYY/os-security-group-rules -X POST -H "X-Auth-Project-Id: petals" -H "User-Agent: python-novaclient" -H "Content-Type: application/json" -H "Accept: application/json" -H "X-Auth-Token: XXX" -d '{"security_group_rule": {"from_port": 33, "ip_protocol": "udp", "to_port": 33, "parent_group_id": "191c96e1-0a4c-4992-804b-b171c3514744", "cidr": "10.0.0.0/16", "group_id": null}}'

    RESP: [200] CaseInsensitiveDict({'date': 'Thu, 09 Jan 2014 11:46:57 GMT', 'x-compute-request-id': 'req-c404fa06-fe68-4cb5-8f11-c1533b50c7a3', 'content-type': 'application/json', 'content-length': '234'})
    RESP BODY: {"security_group_rule": {"from_port": 33, "group": {}, "ip_protocol": "udp", "to_port": 33, "parent_group_id": "191c96e1-0a4c-4992-804b-b171c3514744", "ip_range": {"cidr": "10.0.0.0/16"}, "id": "23584c07-1912-446d-a53d-a7eb17024be4"}}

    +-------------+-----------+---------+-------------+--------------+
    | IP Protocol | From Port | To Port | IP Range    | Source Group |
    +-------------+-----------+---------+-------------+--------------+
    | udp         | 33        | 33      | 10.0.0.0/16 |              |
    +-------------+-----------+---------+-------------+--------------+

### Adding rule to an existing group (secgroup-add-group-rule)

    nova help secgroup-add-group-rule
    ...
    Add a source group rule to a security group.

    Positional arguments:
      <secgroup>      ID or name of security group.
      <source-group>  ID or name of source group.
      <ip-proto>      IP protocol (icmp, tcp, udp).
      <from-port>     Port at start of range.
      <to-port>       Port at end of range.

So let's add a rule to the demo group from petals source...

    nova --debug secgroup-add-group-rule demo petals TCP 8081 8081
    ...
    REQ: curl -i http://host:8774/v2/bf6110e105824ae2b412c7db53d4d79a/os-security-group-rules -X POST -H "X-Auth-Project-Id: petals" -H "User-Agent: python-novaclient" -H "Content-Type: application/json" -H "Accept: application/json" -H "X-Auth-Token: XXX" -d '{"security_group_rule": {"from_port": 8081, "ip_protocol": "TCP", "to_port": 8081, "parent_group_id": "191c96e1-0a4c-4992-804b-b171c3514744", "cidr": null, "group_id": "f303e1dc-3997-40f2-8683-436f4e67e7c4"}}'

    RESP: [200] CaseInsensitiveDict({'date': 'Thu, 09 Jan 2014 11:57:45 GMT', 'x-compute-request-id': 'req-a8513348-9dfb-40c9-94d2-79490d2023fe', 'content-type': 'application/json', 'content-length': '282'})
    RESP BODY: {"security_group_rule": {"from_port": 8081, "group": {"tenant_id": "bf6110e105824ae2b412c7db53d4d79a", "name": "petals"}, "ip_protocol": "tcp", "to_port": 8081, "parent_group_id": "191c96e1-0a4c-4992-804b-b171c3514744", "ip_range": {}, "id": "3de56779-73b8-4dc7-aeed-9fdff00c41b7"}}

    +-------------+-----------+---------+----------+--------------+
    | IP Protocol | From Port | To Port | IP Range | Source Group |
    +-------------+-----------+---------+----------+--------------+
    | tcp         | 8081      | 8081    |          | petals       |
    +-------------+-----------+---------+----------+--------------+

And list the rules for the demo group:

    nova secgroup-list-rules demo
    ...
    +-------------+-----------+---------+-------------+--------------+
    | IP Protocol | From Port | To Port | IP Range    | Source Group |
    +-------------+-----------+---------+-------------+--------------+
    | udp         | 33        | 33      | 10.0.0.0/16 |              |
    | tcp         | 8081      | 8081    |             | petals       |
    | tcp         | 8080      | 8080    |             | demo         |
    | icmp        | -1        | -1      | 0.0.0.0/0   |              |
    | tcp         | 22        | 22      | 0.0.0.0/0   |              |
    +-------------+-----------+---------+-------------+--------------+

And for petals?

    nova secgroup-list-rules petals
    ...
    +-------------+-----------+---------+-----------+--------------+
    | IP Protocol | From Port | To Port | IP Range  | Source Group |
    +-------------+-----------+---------+-----------+--------------+
    | icmp        | -1        | -1      | 0.0.0.0/0 |              |
    | tcp         | 22        | 22      | 0.0.0.0/0 |              |
    +-------------+-----------+---------+-----------+--------------+



