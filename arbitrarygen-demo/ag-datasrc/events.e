<?xml version="1.0" encoding="UTF-8"?>
<Events
    package="cc.suitalk.arbitrarygen.demo.e"
    delegateTag="event-delegate"
    delegate="RxEventCollection"
    tag="event">

    <event name="DefaultEvent" >
        <field name="name" type="String" default="null"/>
    </event>

    <event name="TestEvent" final="true" parent="DefaultTestEvent">
        <field name="name" type="String" default="null"/>
        <field name="type" type="int" default="0"/>
        <data static="true" final="true">
            <field name="errCode" type="int"/>
        	<field name="errType" type="int" default="0"/>
        </data>
    </event>

    <event name="XmlThirdEvent">
        <data>
            <field name="errCode" type="int"/>
        	<field name="errType" type="int" default="0"/>
        </data>
    </event>
    <event name="XmlFourthEvent">
        <data>
            <field name="errCode" type="int">
                <constant name="ERR_CODE_NORMAL" value="0"/>
            </field>
        	<field name="errType" type="int" default="0">
                <constant name="ERR_TYPE_NORMAL" value="0"/>
                <constant name="ERR_TYPE_NET" value="1"/>
            </field>
        </data>
        <CallBack>
            <field name="trigger" type="String"/>
        </CallBack>
        <result>
            <field name="result" type="String"/>
        </result>
    </event>
</Events>