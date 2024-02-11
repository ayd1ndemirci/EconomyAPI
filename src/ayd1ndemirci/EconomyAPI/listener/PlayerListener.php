<?php

namespace ayd1ndemirci\EconomyAPI\listener;

use ayd1ndemirci\EconomyAPI\database\SQLite;
use ayd1ndemirci\EconomyAPI\Economy;
use pocketmine\event\Listener;
use pocketmine\event\player\PlayerJoinEvent;
use SOFe\AwaitGenerator\Await;

class PlayerListener implements Listener
{
    public SQLite $database;

    public function __construct()
    {
        $this->database = Economy::getInstance()->getDatabase();
    }

    public function onPlayerJoin(PlayerJoinEvent $event):void
    {
        $player = $event->getPlayer();
        Await::f2c(function () use($player) {
            $rows = (array) yield from $this->database->getPlayerMoney($player->getName());
            if(empty($rows)) $this->database->addPlayer($player->getName());
        });
    }
}